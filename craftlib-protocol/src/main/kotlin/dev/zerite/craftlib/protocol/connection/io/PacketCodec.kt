package dev.zerite.craftlib.protocol.connection.io

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.RawPacket
import dev.zerite.craftlib.protocol.wrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec

/**
 * Writes packet IDs into and reads from the buffer.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class PacketCodec(private val connection: NettyConnection) : ByteToMessageCodec<Packet>() {

    /**
     * The inverted direction used for decoding packets.
     */
    private val decodeDirection = connection.direction.invert()

    /**
     * Encodes a packet and writes it into the buffer.
     *
     * @param  ctx      The channel context.
     * @param  packet   The packet to write to this buffer.
     * @param  buf      The buffer which we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun encode(ctx: ChannelHandlerContext, packet: Packet, buf: ByteBuf) {
        val buffer = buf.wrap(connection)

        // Get the packet registry data
        val data = connection.state[connection.direction][connection.version, packet]
            ?: return run {
                val raw = packet as? RawPacket ?: return@run
                buffer.writeVarInt(raw.id)
                buffer.writeBytes(raw.data)
            }

        // Write the packet data
        buffer.writeVarInt(data.id)
        @Suppress("UNCHECKED_CAST")
        (data.io as? PacketIO<Packet>)?.write(buffer, connection.version, packet, connection)
    }

    /**
     * Reads a packet from the buffer based on their ID, then maps it to
     * the packet class we need and adds it to the output.
     *
     * @param  ctx       The channel context.
     * @param  buf       The buffer we are reading from.
     * @param  out       List of all the outputs we've read.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val buffer = buf.wrap(connection)
        val id = buffer.readVarInt()

        // Get the packet registry data
        val io = connection.state[decodeDirection][connection.version, id]?.io

        if (io != null && !ctx.channel().isOpen) return

        // Read the data fully
        val packet = (io?.read(buffer, connection.version, connection)
            ?: RawPacket(id, buffer.readByteArray(buf.readableBytes())))
            .apply { out.add(this) }

        /*
         * Check if the packet didn't read all of its bytes.
         * If we don't do this the next packet will have some old data from this packet in its buffer and mess up
         * the entire packet reading pipeline
         */
        if (buffer.readableBytes > 0)
            error("Packet ${packet::class.java.simpleName} wasn't fully read (${buffer.readableBytes} bytes left)")
    }
}
