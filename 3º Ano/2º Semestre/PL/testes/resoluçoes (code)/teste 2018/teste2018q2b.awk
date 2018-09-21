BEGIN { FS=" : " }

$0 ~ /.....+/   {linha++;
				if(livros[$1]==0) {
					livros[$1]=linha;
					print $0;
				}
				else {
					print "=="livros[$1]"=="$0;
				}
				}
