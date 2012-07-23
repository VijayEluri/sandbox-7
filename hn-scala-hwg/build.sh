#!/bin/sh

platex -shell-escape $1.tex
platex -shell-escape $1.tex
mendex hn-scala-hwg.idx
platex -shell-escape $1.tex
dvipdfmx $1
