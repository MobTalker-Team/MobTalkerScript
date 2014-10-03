# MobTalkerScript
MobTalkerScript is a scripting language specifically designed for the Minecraft Modification MobTalker2.

For more information about MobTalkerScript, visit the [MobTalker2 Wiki](http://www.mobtalker.net/wiki/MobTalkerScript2).


## Compiling
1. Install Git and Gradle
2. Clone the repository
3. Run 'gradle build' to build the project


## Contributing
### Creating an issue
If you're not a programmer or aren't comfortable with submitting a Pull Request,
you can submit an issue report to the tracker. That way other contributors can start working on fixing it.

### Submitting a Pull Request
If you want to help the project by fixing bugs or implementing new features, 
you can fork the repository and submit a Pull Request.

1. Fork the repository (develop or master, depending of the nature of the PR)
2. Run 'gradle generateAntlr'
3. Setup your workspace
    - For Eclipse, run 'gradle eclipse'
    - For IntelliJ, run 'gradle idea'
4. Make your changes
5. Submit your PR

MobTalkerScript uses [git-flow](https://www.atlassian.com/git/workflows#!workflow-gitflow) as its workflow. PRs that do not follow this workflow are rejected immediately.

- Features must branched from `develop` and be prefixed with `feature/`.
- Bugfixes are made directly on `develop`.
- If you're submitting a critical bugfix, branch from `master` and prefix the branch with `hotfix/`.

To increase the chances that your PR gets accepted, follow these basic rules

- Follow the git-flow rules.
    - **Never** commit to `master`. Only project members are allowed to make releases.
- Include the license header on top of your files.
- Follow the general coding style of the rest of the project (e.g. spaces instead of tabs).


## License & Copyright
MobTalkerScript is Copyright (C) 2013 - 2014 Chimaine and licensed under the LGPLv3.
For more information, see the LICENSE file or http://www.gnu.org/licenses/.