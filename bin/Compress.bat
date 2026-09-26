@rem Why LFS is still not a part of stock Git after being out for over a decade is beyond me.
@rem Also, how does *one* design a version control system without ever thinking about support for arbitrarily-sized files?

@UPX.exe --color --verbose --lzma --best --compress-exports=0 --compress-icons=0 --compress-resources=0 --strip-relocs=0 --overlay=copy --no-backup --force-overwrite FFMPEG.exe
