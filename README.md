# com.semchyshyn:opart

Yuriy's tools for algorithmic generation of optical art pieces (still and animated).

## Overview

This repository contains a personal *passion project* with no significance whatsoever.
You should probably go back to wherever you came from.
It is extremely unlikely that you actually need any of this.

But hey, if you're still here — welcome!
Look around and feel free to take whatever you want.
The whole thing has been dedicated to the public domain under Creative Commons' `CC0` license.

No pull request will be approved under any circumstances.
You should reach out to Yuriy directly if this feels wrong.
Or if you want to discuss anything else, really.

## Setup

 1. ### Integrated Development Environment
	As of late 2025, however sad it might be to admit, IntelliJ IDEA seems to be the best available option, as far as Java IDEs are concerned.
	  * Microsoft's **Visual Studio Code** is compact, well-integrated, and has an acceptable UI; but fails to properly handle Java even after extensive customization.
	  * Eclipse Foundation's **Eclipse** still offers adequate capabilities with a reasonable UI; but has also become somewhat bloated while still requiring manual installation of tools.
	  * Apache's **NetBeans** looks as awesome as ever with its beautifully clean UI; but seriously lacks in capabilities and does not quite work out-of-the-box.
	  * JetBrains' **IntelliJ IDEA** has a godawful UI and comes atrociously misconfigured by default; yet it is extremely powerful, *just works* immediately upon installation, and even takes care of pulling all the necessary tools up (JDK, Maven, Git, MinGW).

	It is indeed utterly terrifying how Russia's ФСБ/СВР (Federal Security and Foreign Intelligence Services) can now take control of literally *everything* on a whim…
	But what can one do, aside from watching the world learn from its mistakes?

	So yeah, go ahead and get yourself the most recent version of [IntelliJ IDEA](https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows).

 2. ### Version Control System
	Configure Git by executing the following commands
	```shell
	git config --global core.ignorecase false
	git config --global core.editor edit
	git config --global user.name "John Doe"
	git config --global user.email jdoe@example.org
	```

 3. ### Authentication and Authorization
	After executing the following commands
	```shell
	ssh-keygen -t rsa -b 16384 -a 1 -C jdoe@example.org
	
	git config --global user.signingkey %UserProfile%\.ssh\id_rsa
	git config --global commit.gpgsign true
	git config --global gpg.format ssh
	```
	post the content of `%UserProfile%\.ssh\id_rsa.pub` to [GitHub](https://github.com/settings/keys) *twice* (yay for usability!)

 4. ### Source Code Repository
	Create a local copy of the repository by executing the following command
	```shell
	git clone git@github.com:5/opart.git OpArt
	```

 5. ### Included Utility Binaries
	Update binaries for [PNGCrush](https://sourceforge.net/projects/pmt/files/pngcrush-executables), [FFMPEG](https://github.com/AnimMouse/ffmpeg-stable-autobuild/releases), and [UPX](https://github.com/upx/upx/releases) located in the `bin` directory, then execute the following command
	```shell
	Compress.bat
	```

## Workflow

 6. ### Imagine
	Imagine a new piece of optical art and define it: first extend the `OpArt` class and implement the `render` method, then instantiate it and invoke the `create` method
	```java
	package org.example.opart;
	
	import com.semchyshyn.opart.animated.*;
	import org.jspecify.annotations.*;
	
	public class Piece extends OpArt {
		protected final @NonNull Value<Double> radius = new Value<>(Range.UNIT_INTERVAL, Change.DECELERATING, Type.DOUBLE);
	
		@Override
		protected void render(final @NonNull Graphics graphics, final double time) {
			graphics.circle(0d, 0d, radius.at(time));
		}
	
		@SuppressWarnings({"UnnecessaryModifier", "unused"})
		public static void main(final @NonNull String... arguments) {
			new Piece().create();
		}
	}
	```

 7. ### Create
	Build the definition and execute it to generate the new piece of optical art
	```shell
	mvn clean package && java -cp .mvn/OpArt.jar org.example.opart.Piece
	```

 8. ### Publish
	Publish the new piece of optical art by manually uploading it to major video sharing platforms
	  * [YouTube](https://www.youtube.com/@an_op_art),
	  * [TikTok](https://www.tiktok.com/@an.op.art), and
	  * [Instagram](https://www.instagram.com/an_op_art)

 9. ### Commit
	Store the definition to GitHub by executing the following command
	```shell
	git add --all && git commit --amend --date=now --no-edit && git push --force
	```

10. ### Clean Up
	Executing the following command from time to time prunes unnecessary metadata from the repository
	```shell
	git reflog expire --expire=all --all --expire-unreachable=now
	```
