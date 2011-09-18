import random

lens = [6, 7]

infile = open("dictionary.txt", "r")
words = [x.strip() for x in infile.readlines() if (len(x.strip()) in lens and '-' not in x)]
infile.close()

def listToString(l):
    s = ''
    for char in l:
        s += char
    return s

def abcorder(s):
    chars = [char for char in s]
    chars.sort()
    return listToString(chars) 

abcs = set([])
bingos = {}
for word in words:
    abc = abcorder(word)
    if abc in abcs:
        bingos[abc].append(word)
    else:
        abcs.add(abc)
        bingos[abc] = [word]

outfile = open("wordlist.js", "w")
text = "wordlist = %s;" % bingos.values()
outfile.write(text)
outfile.close()
