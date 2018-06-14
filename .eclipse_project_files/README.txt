This directory contains sets of Eclipse project files which can be used to import a fully-configured project so that it "just works".

I don't like the idea of forcing a particular kind of setup on others, though, so they haven't been placed in the project root.

To use them, copy them to the project root before importing.  Project files in the root directory are ignored by .gitignore.  This approach allows easily-configured projects while also avoiding "project file fights" in commits.


/Buildship:  Work in the default Java or Java EE perspective.
             Requires the Buildship plugin, which is included
             in some versions of Eclipse.  If you don't have
             it, you can get it from the Eclipse Marketplace.
