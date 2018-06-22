#!/bin/bash

for i in *.png
	do
		printf $i
		convert -resize x140 $i $i
	done
