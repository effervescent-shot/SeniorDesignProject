#!/usr/bin/python
import io, sys, os
import numpy as np
import pandas as pd
import matplotlib as mpl
import matplotlib.pylab as plt
from numpy import genfromtxt
import csv
import getopt
from scipy import stats  
import seaborn as sns




def main():
	print "Shell we begin?"
	#print 'PATH3:\n',path3load['Mean']
	#print  path3load['Std']
	#print 'PATH1:\n',path1load['Mean']
	#print  path1load['Std']
	path3load = pd.read_csv('load3.csv', sep=',', header=0)
	path1load = pd.read_csv('load1.csv', sep=',', header=0)

	
	path1delay = np.genfromtxt('packet1.csv', delimiter=',')
	path3delay = np.genfromtxt('packet3.csv', delimiter=',')
	
	time = sys.argv[1]
	time = int(time)
	print time


	links3 = path3load['Links:']
	path3T = path3load.iloc[:, range(1,time+1)]
	path3_mean_t = path3T.mean(axis=0)
	path3_std_t = path3T.std(axis=0)
	path3_mean_l = path3T.mean(axis=1)
	path3_std_l = path3T.std(axis=1)


	links1 = path1load['Links:']
	path1T = path1load.iloc[:, range(1,time+1)]
	path1_mean_t = path1T.mean(axis=0)
	path1_std_t = path1T.std(axis=0)


	sns.set()
	sns.set(color_codes=True)

	plt.figure(1, figsize=(10,8))                # the first figure
	
	plt.subplot(211)             # the first subplot in the first figure
	plt.plot(range(1,time+1), path3_mean_t, 'ro', label='path3_load_mean')
	plt.plot(range(1,time+1), path1_mean_t, 'bo', label='path1_load_mean' )
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Mean Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(path3_mean_t)+50, ymin = 0)
	plt.legend(loc=1)

	plt.subplot(212)             # the second subplot in the first figure
	plt.plot(range(1,time+1), path3_std_t, 'ro', label= 'path3_load_std')
	plt.plot(range(1,time+1), path1_std_t, 'bo', label= 'path1_load_std')
	plt.xlabel('Time(sec)', fontsize = 12)
	plt.ylabel('Std Load', fontsize = 12)
	plt.ylim(ymax = max(path3_std_t)+50, ymin = 0)
	plt.legend(loc=1)
	#plt.show()	



	
	plt.figure(2, figsize=(10,10)) 
	plt.subplot(211)
	plt.xlabel('Delivery Time(msec)', fontsize = 12)
	plt.ylabel('Relative Frequency', fontsize = 12)
	sns.distplot(path3delay, kde=True, hist=True, bins=20, fit=stats.invgauss);
	plt.title('Path3 Packet Delivery Time', fontsize = 12 )
	

	plt.subplot(212)
	plt.xlabel('Delivery Time(msec)', fontsize = 12)
	plt.ylabel('Relative Frequency', fontsize = 12)
	sns.distplot(path1delay, kde=True, hist=True, bins=20, fit=stats.invgauss);
	plt.title('Path1 Packet Delivery Time', fontsize = 12 )

	plt.show()

main()