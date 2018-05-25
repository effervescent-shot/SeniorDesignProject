#!/usr/bin/python
import io, sys, os
import numpy as np
import pandas as pd
import matplotlib as mpl
import matplotlib.pylab as plt
from numpy import genfromtxt
import csv
import getopt






#print("Shell we begin?")

#path3load = np.genfromtxt('path3_1.csv', delimiter=",")


def main():
	print "Shell we begin?"
	#print 'PATH3:\n',path3load['Mean']
	#print  path3load['Std']
	#print 'PATH1:\n',path1load['Mean']
	#print  path1load['Std']
	path3load = pd.read_csv('path3_1.csv', sep=',', header=0)
	path1load = pd.read_csv('path1_1.csv', sep=',', header=0)

	numDisj = sys.argv[1]
	numDisj = int(numDisj)
	print numDisj


	links3 = path3load['Links:']
	path3T = path3load.iloc[:, range(1,numDisj+1)]
	path3_mean_t = path3T.mean(axis=0)
	path3_std_t = path3T.std(axis=0)
	path3_mean_l = path3T.mean(axis=1)
	path3_std_l = path3T.std(axis=1)



	links1 = path1load['Links:']
	path1T = path1load.iloc[:, range(1,numDisj+1)]
	path1_mean_t = path1T.mean(axis=0)
	path1_std_t = path1T.std(axis=0)



	plt.figure(1, figsize=(15,8))                # the first figure
	
	plt.subplot(211)             # the first subplot in the first figure
	plt.plot(range(1,numDisj+1), path3_mean_t, 'r+', label='path3_load_mean')
	plt.plot(range(1,numDisj+1), path1_mean_t, 'bo', label='path1_load_mean' )
	plt.xlabel('Time' , fontsize = 14)
	plt.ylabel('Mean Load - bps', fontsize = 14)
	plt.legend(loc=1)
	#plt.show()

	#plt.figure(2)
	plt.subplot(212)             # the second subplot in the first figure
	plt.plot(range(1,numDisj+1), path3_std_t, 'r+', label= 'path3_load_std')
	plt.plot(range(1,numDisj+1), path1_std_t, 'bo', label= 'path1_load_std')
	plt.xlabel('Time', fontsize = 14)
	plt.ylabel('Std Load', fontsize = 14)
	plt.legend(loc=4)


	#plt.subplot(313)             # the second subplot in the first figure
	#plt.plot( range(1, links3.size+1), path3_mean_l, 'r+', label= 'link_load_mean')
	#plt.plot( range(1, links3.size+1), path3_std_t, 'bo', label= 'link_load_std')
	#plt.xlabel('Links', fontsize = 14)
	#plt.ylabel('Load', fontsize = 14)
	#plt.legend(loc=4)


	plt.show()	





main()