# com.semchyshyn:opart

Yuriy's tools for algorithmic generation of optical art pieces (still and animated).

## Overview

This repository contains a personal "passion project" with no significance whatsoever.
You should probably go back to wherever you came from.
It is extremely unlikely that you actually need any of this.

* If, for any reason, you want to use any of it for any reason whatsoever, you're free to do whatever you want with it.
  The entirety of this project has been dedicated to the public domain under Creative Commons' CC0 license.

No pull requests will be approved under any circumstances.
Feel free to reach out to Yuriy directly if that feels wrong to you.
Or if you want to discuss anything else, really.

## Setup

 1. ### Integrated Development Environment
	As of late 2025, however sad it might be to admit, IntelliJ IDEA seems to be the best available option, as far as Java IDEs are concerned:
	* Microsoft's **Visual Studio Code** is compact, well-integrated, and has an acceptable UI; yet it struggles to work with Java even after extensive customization.
	* Eclipse Foundation's **Eclipse** still offers adequate capabilities with a reasonable UI, but became somewhat bloated and does not work out-of-the-box requiring non-trivial customization and installation of dependencies.
	* Apache's **NetBeans** looks as awesome as ever, with beautifully intuitive UI, but lacks in capabilities, and does not work out-of-the-box requiring some amount of configuration.
	* JetBrains' **IntelliJ IDEA** got a godawful UI and every single configuration option set to the exactly wrong value by default, it is also very powerful, just works immediately upon installation, and even allows installation of all necessary tools (JDK, Maven, Git, MinGW) from within the IDE.

	It is indeed utterly terrifying how Russia's ФСБ/СВР (Federal Security and Foreign Intelligence Services) can now take control of literally *everything* on a whim…
	But what can one do, aside from watching the world learn from its mistakes?

	So yeah, go ahead and download the most recent version of [IntelliJ IDEA](https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows), install, and configure it.

 2. ### Version Control System
	Run the following commands to set 
	```shell
	git config --global user.name "John Doe"
	git config --global user.email jdoe@example.org
	git config --global core.editor edit
	```

 3. ### Authentication and Authorization
	```shell
	ssh-keygen -t rsa -b 16384 -a 1 -C jdoe@example.org
	
	git config --global commit.gpgsign true
	git config --global gpg.format ssh
	git config --global user.signingkey %UserProfile%\.ssh\id_rsa
	```
	add the contents of `%UserProfile%\.ssh\id_rsa.pub` to [GitHub](https://github.com/settings/keys) *twice* (yay for usability!)

 4. ### Source Code Repository
	```shell
	git clone git@github.com:5/opart.git OpArt
	```

## Workflow

 5. ### Change
	…

 6. ### Execute
	…

 7. ### Commit
	```shell
	git add --all && git commit --amend --no-edit && git push --force
	```

 8. ### Clean Up
	Execute the following command from time to time  clean the repository's metadata:
	```shell
	git reflog expire --expire=all --all --expire-unreachable=now
	```
