## Contributing
When contributing to **CraftLib**, there are certain guidelines to be
followed for a pull request to be **approved**.

**Note:** It is recommended to create a new branch on your fork for each pull request you submit.

#### Changing Code
1. Adding a new class or method
    * All code changes should be properly documented with JavaDoc.
        * This should include the author (`@author`), version introduced (`@since`) and
        list of parameters for the function (`@param`).
    * Write **consistent code**
        * Code changes should use a similar structure to existing files.
        * Ensure you are using any existing utilities before making your own. 
        * All packages should be in proper packages, you should rarely need to
        create a new package unless it is **necessary**.
    * **DO NOT** remove existing functionality or tools. Use deprecation instead.
2. Authoring commits
    * This project uses the [GitMoji](https://gitmoji.carloscuesta.me/) specification.
        * **Please refrain** from using raw emoji in your commits, and instead use
        their shortcodes. (`:smile:`)
    * Although creating many commits may seem tempting, please try to use a minimal
    amount. Commits shouldn't be seen as a statistic to impress others.
    * Branches should be named appropriately, describing their purpose.
        * e.g `feature/bot` or `bug/example`
    * Your code **must be** formatted alike other files and not restructure
    files not relating to it.
3. Creating a Pull Request
    * Ensure your fork is **fully updated** *before* you create your pull request.
    * Open PR's on the correct branches.