import random
def main():
	name = raw_input("Enter a name of a test machine: ")
	f = open(name+"Metadata.txt", 'w+')
	w=random.randint(5,10)
	h=random.randint(5,10)
	d=random.randint(5,10)

	
	items = {}
	f.write(name+" "+str(w)+" "+str(h)+" "+str(d)+" "+name+"Data.txt")
	for line in open('Items.txt'):
		line = line.split()
		items[line[0]] = int(line[1])
	#items = ["Cheese","Chips","Pretzels","Tacos","M&Ms",'Chocolate']
	f = open(name+"Data.txt", 'w+')
	for x in range(w):
		for y in range(h):
			r = random.choice(items.keys())
			f.write(str(x)+" "+str(y)+" "+r+" "+str(items[r])+" "+str(random.randint(1,31))+"/"+str(random.randint(1,12))+"/"+str(random.randint(1990,2014))+" "+str(random.randint(1,d))+"\n")

main()
