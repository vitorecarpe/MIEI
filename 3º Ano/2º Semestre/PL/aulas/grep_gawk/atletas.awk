BEGIN { FS = "\t";
	print "<html>\n\t<body>\n\t<table border='1'>";
      }

      { print "<tr><td>" $20 "<\td><td>" $20 "<\td><td>" $27 "</td></tr>" }

END   { print "</table>\n</body>\n</html>" }
