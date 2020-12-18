moeglicheteile = {
"pos1": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos2": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos3": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos4": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos5": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos6": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos7": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos8": [1, 2, 3, 4, 5, 6, 7, 8, 9],
"pos9": [1, 2, 3, 4, 5, 6, 7, 8, 9]
}
seitenpos1 = {
    1: [1, 2, 3],
    2: [1, 2, 3],
    3: [1, 2, 3],
    4: [1, 2, 3],
    5: [1, 2, 3],
    6: [1, 2, 3],
    7: [1, 2, 3],
    8: [1, 2, 3],
    9: [1, 2, 3],
}
gesetzteteile = {
    "pos1": [' '],
    "pos2": [' '],
    "pos3": [' '],
    "pos4": [' '],
    "pos5": [' '],
    "pos6": [' '],
    "pos7": [' '],
    "pos8": [' '],
    "pos9": [' ']
}
beruehrendeseiten = {
                    "pos1": {"pos2": [1,0], "pos6": [2,1], "pos7": [0,2]},
                    "pos2": {"pos1": [0,1], "pos3": [2,0]},
                    "pos3": {"pos2": [0,2], "pos4": [2,1], "pos8": [1,0]},
                    "pos4": {"pos3": [1, 2], "pos5": [0, 1]},
                    "pos5": {"pos4": [1,0], "pos6": [0,2], "pos9": [2,1]},
                    "pos6": {"pos1": [1,2], "pos5": [2,0]},
                    "pos7": {"pos1": [2,0]},
                    "pos8": {"pos3": [0,1]},
                    "pos9": {"pos5": [1,2]},
}
teile = {}
with open("Puzzle1.txt") as data:
    s1 = ""
    s2 = ""
    s3 = ""
    temp = 0
    for line in data:
        line = line.replace("\n", "")
        if temp == 0 or temp == 1:
            0
        else:
            s1, s2, s3 = line.split(" ")
            tempstr = "teil" + str(temp - 1)
            teile[tempstr] = [int(s1),int(s2), int(s3)]
            teile4checking = teile.copy()
        temp += 1
def drehen(stelle):
    stelle = "teil" + str(stelle)
    teile[stelle] = [teile[stelle][2], teile[stelle][0], teile[stelle][1]]
    return teile
def drehen4check(teilenummer):
    stelle = "teil" + str(teilenummer)
    teile4checking[stelle] = [teile4checking[stelle][2], teile4checking[stelle][0], teile4checking[stelle][1]]
    return teile4checking
def figursetzen(position, teilenummer):
    gesetzteteile["pos" + str(position)] = teile["teil" + str(teilenummer)]
    teile["teil" + str(teilenummer)] = [' ']
def korrigierenfigur(position, teilenummer):
    teile["teil" + str(teilenummer)] = teile4checking["teil" + str(teilenummer)]
    gesetzteteile["pos" + str(position)] = [' ']
def teilanplatz(position):
    for i in range(1, 10):
        for j in range(3):
            if gesetzteteile["pos" + str(position)] == teile4checking["teil" + str(i)]:
                return i
            else:
                drehen4check(i)
def ueberpruefen(stelle, teilenummer):
    if teile["teil" + str(teilenummer)] == [" "]:
        return False
    seitestelle = beruehrendeseiten["pos" + str(stelle)]
    keylist = list(seitestelle.keys())
    sidecounter = 0
    for q in seitestelle:
        if not gesetzteteile[q] == [' ']:
            sidecounter += 1
    if sidecounter <= 0:
        return True
    for j in range(0,3):
        try:
            keylist[j]
            if not gesetzteteile[str(keylist[j])] == [' ']:
                if (-1 * gesetzteteile[keylist[j]][seitestelle[keylist[j]][1]]) == teile["teil" + str(teilenummer)][seitestelle[keylist[j]][0]]:
                    sidecounter -= 1
                else:
                    return False
        except IndexError:
            0
    if sidecounter <= 0:
        return True
def keineloesung():
    print("Keine loesung")
    open("Keine_Loesung.txt", "w").write("Keine Loesung!")
    raise SystemExit
def loesen(counter, moeglicheteile):
    try:
        if counter == 10:
            file = open("Loesung.txt", 'w')
            file.write("GELOEST!\n")
            print("geloest")
            for i in range(1,10):
                file.write("Platz " + str(i) + ": Teil"+ str(teilanplatz(i)) + "\n")
            file.write("oder: " + str(gesetzteteile))
            raise SystemExit
        for j in range (1, len(teile)+1):
            for l in range(3):
                if ueberpruefen(counter,j) and j in moeglicheteile["pos" + str(counter)]:
                    if counter == 1:
                        q = len(seitenpos1[j])
                        if not q == 3:
                            drehen(j)
                    figursetzen(counter, j)
                    loesen(counter+1, moeglicheteile)
                else:
                    if not teile["teil" + str(j)] == [' ']:
                        drehen(j)
        if not counter == 1:
            x = teilanplatz(counter - 1)
            korrigierenfigur(counter-1, x)
            for m in range(counter, 10):
                moeglicheteile["pos" + str(m)] = [1, 2, 3, 4, 5, 6, 7, 8, 9]
            if counter-1 == 1:
                if len(seitenpos1[x]) == 1:
                    seitenpos1.pop(x)
                    moeglicheteile["pos" + str(counter - 1)].remove(x)
                    loesen(counter - 1, moeglicheteile)
                try:
                    seitenpos1[x].pop()
                except KeyError:
                    keineloesung()
                loesen(counter - 1, moeglicheteile)
            moeglicheteile["pos" + str(counter - 1)].remove(x)
            loesen(counter-1, moeglicheteile)
    except RecursionError:
        keineloesung()
loesen(1, moeglicheteile)
