import random
import csv
import math
import operator
from datetime import datetime
import time

def loadCsv(filename):
	lines = csv.reader(open(filename,'rt'))
	dataset = list(lines)
	return dataset[1:2000]

def clean_and_transform_dataset(dataset):
	transformed_dataset = []
	for i in range(1, len(dataset)):
		new_row = []
		row = dataset[i]
		new_row.append(row[0])
		startTime = datetime.strptime(row[1], '%Y-%m-%d %H:%M:%S')
		endTime = datetime.strptime(row[2], '%Y-%m-%d %H:%M:%S')
		tripTime = endTime.timestamp() - startTime.timestamp()
		new_row.append(tripTime)
		new_row.append(row[3])
		new_row.append(row[5])
		new_row.append(row[8])
		transformed_dataset.append(new_row)
	return transformed_dataset

def splitDataset(dataset, splitratio):
	trainSize = int(len(dataset)*splitratio)
	trainSet=[]
	copy = list(dataset)
	while(len(trainSet)<trainSize):
		index = random.randrange(len(copy))
		trainSet.append(copy.pop(index))
	return [trainSet, copy]

def euclideanDistance(instance1, instance2, length):
	distance = 0.0
	for i in range(length):
		distance += pow((float(instance1[i])-float(instance2[i])),2)
	return math.sqrt(distance)


def getNeighbours( item, trainSet, k):
	distances = []
	length = len(trainSet[0])-1
	for x in range(len(trainSet)):
		dist = euclideanDistance(item, trainSet[x], length)
		distances.append((trainSet[x],dist))
	distances.sort(key = operator.itemgetter(1))
	neighbours = []
	for x in range(k):
		neighbours.append(distances[x][0])
	return neighbours

def getAnswer(neighbours):
	classVotes = {}
	for i in range(4):
		response = neighbours[i][-1]
		if response in classVotes:
			classVotes[response] += 1
		else : 
			classVotes[response] = 1
	sortedVotes = sorted(classVotes.items(), key = operator.itemgetter(1), reverse = True)
	return classVotes

def getAccuracy(predictions,testSet):
	correct = 0
	for i in range(len(predictions)):
		if (predictions[i] == testSet[i][-1]):
			correct += 1
	return (correct / float(len(predictions)))*100

if __name__ == "__main__" : 

	dataset = loadCsv('trip.csv')

	dataset = clean_and_transform_dataset(dataset)

	splitratio = 0.70

	trainSet, testSet = splitDataset(dataset, splitratio)

	predictions = []

	start = time.time()

	for item in testSet:
		neighbours = getNeighbours(item, trainSet, 4)
		answer = getAnswer(neighbours)
		predictions.append(answer)

	accuracy = getAccuracy(predictions, testSet)

	end = time.time()

	print("Accuracy : ", accuracy)

	print("Time took : ", end-start)


