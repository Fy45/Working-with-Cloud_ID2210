import shutil
import os
import time

def file_list(file_dir):
	fileList = []
	for root, dirs, files in os.walk(file_dir):
		for file in files:
			fileList.append(os.path.join(root,file))
	return fileList

def get_size(filepath):
	filepath = unicode(filepath,'utf8')
	filesize = os.path.getsize(filepath)/float(1024*1024)
	return round(filesize,2) # round the file size with accuracy of 2 decimal points

def cal_rate(filesize,beginTime,endTime):
	rate = filesize/(endTime-beginTime)
	return round(rate,2)

def main():
	fileList = []
	filesize = []
	wTime = []
	rTime = []
	wRate = []
	rRate = []

	fileList = file_list("/opt/test")
	for fileName in fileList:

		size = get_size(fileName)
		filesize.append(size)

		

		#upload local file into bucket
		beginTimeu = time.time()
		shutil.move(fileName,fileName.replace("test","Download"))
		endTimeu = time.time()
		writingTD= endTimeu-beginTimeu
		wTime.append(writingTD)
		writing = cal_rate(size,beginTimeu,endTimeu)
		wRate.append(writing)

		#download bucket file to a local folder
		beginTimed = time.time()
	    shutil.move(fileName.replace("test","Download"),fileName)
		endTimed = time.time()
		readingTD = endTimed-beginTimed
		rTime.append(readingTD)
		reading = cal_rate(size,beginTimed,endTimed)
		rRate.append(reading)

		#print out the analysis result for individual files
		print "File_Name",fileName
		print "File_Size",size,"mb"
		print "Uploading time", writingTD,"s"
		print "Downloading time",readingTD,"s"
		print "Rate of writing",wRate,"mb/s"
		print "Rate of reading",rRate,"mb/s","\n"


		#calculate the total storage performance
	totalSize = 0
	for total_size in filesize:
		totalSize = totalSize+total_size

	totalWriteTime = 0 
	for total_time in wTime:
		totalWriteTime = totalWriteTime+total_time

	totalReadTime = 0
	for total_time in rTime:
		totalReadTime = totalReadTime+total_time

		#calculate the average rate of reading and writing
	averageW = round(totalSize/totalWriteTime)
	averageR = round(totalSize/totalReadTime)

	print "Total file size are :",totalSize,"mb"
	print "uploading time:",totalWriteTime,"s"
	print "downloading time:",totalReadTime,"s"
	print "Speed of writing(AVG):",averageW,"mb/s"
	print "Speed of reading(AVG):",averageR,"mb/s","\n"

if __name__ == '__main__':
	main()