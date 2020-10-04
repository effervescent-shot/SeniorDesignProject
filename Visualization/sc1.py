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
from scipy.interpolate import spline
import plotly.plotly as py
import plotly.figure_factory as ff




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
	path3_total_t = path3T.sum(axis=0)


	links1 = path1load['Links:']
	path1T = path1load.iloc[:, range(1,time+1)]
	path1_mean_t = path1T.mean(axis=0)
	path1_std_t = path1T.std(axis=0)
	path1_total_t = path1T.sum(axis=0)


	print("p1 mean_std: ", path1_std_t.mean())
	print("p3 mean_std: ", path3_std_t.mean())

	sns.set()
	sns.set(color_codes=True)

	plt.figure(1, figsize=(10,8))      # the first figure
	plt.subplot(311) 
	x1 = range(101,time-69+1+19)
	conv3_20 = np.convolve(path3_total_t, np.full ((20),0.05))
	conv1_20 = np.convolve(path1_total_t, np.full ((20),0.05))
	plt.plot(x1, conv3_20[100:950], label='P3_total_load')
	plt.plot(x1, conv1_20[100:950], label='P1_total_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Total Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(conv3_20)+30, ymin = 0)
	plt.legend(loc=4)
	

	plt.subplot(312) 
	x2 = range(101,time-69+1+19)
	mconv3_20 = np.convolve(path3_mean_t,np.full ((20),0.05))
	mconv1_20 = np.convolve(path1_mean_t,np.full ((20),0.05))
	plt.plot(x2, mconv3_20[100:950], label='P3_mean_load')
	plt.plot(x2, mconv1_20[100:950], label='P1_mean_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Mean Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(mconv3_20)+10, ymin = 0)
	plt.legend(loc=4)

	plt.subplot(313) 
	x3 = range(101,time-69+1+19) 
	sconv3_20 = np.convolve(path3_std_t,np.full ((20),0.05))
	sconv1_20 = np.convolve(path1_std_t,np.full ((20),0.05))
	plt.plot(x3, sconv3_20[100:950], label='P3_load_std')
	plt.plot(x3, sconv1_20[100:950], label='P1_load_std')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Standard Deviation', fontsize = 12)
	plt.ylim(ymax = max(sconv1_20)+10, ymin = 0)
	plt.legend(loc=4)
	


	plt.figure(2, figsize=(10,10)) 
	plt.subplot(211)
	plt.xlabel('Delivery Time(msec)', fontsize = 12)
	plt.ylabel('Relative Frequency', fontsize = 12)
	plt.xlim(0,1500)
	ax = sns.distplot(path3delay, kde=False, hist=True, fit=stats.invgauss);
	plt.title('Path3 Packet Delivery Time', fontsize = 12 )
	

	plt.subplot(212)
	plt.xlabel('Delivery Time(msec)', fontsize = 12)
	plt.ylabel('Relative Frequency', fontsize = 12)
	plt.xlim(0,1500)
	bx = sns.distplot(path1delay, kde=False, hist=True, fit=stats.invgauss);
	plt.title('Path1 Packet Delivery Time', fontsize = 12 )


	plt.show()

main()