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


def read_load_files(filenames,time):
	load_aug_mean = 0
	load_aug_total = 0
	load_aug_std = 0
	n= len(filenames)

	for file in filenames:
		p_load = pd.read_csv( file, sep=',', header=0)
		p = p_load.iloc[:, range(1,time+1)]
		p_mean = p.mean(axis=0)
		p_total = p.sum(axis=0)
		p_std = p.std(axis=0)
		load_aug_mean += p_mean + np.zeros(len(p_mean))
		load_aug_total += p_total + np.zeros(len(p_total))
		load_aug_std += p_std + np.zeros(len(p_std))

	return load_aug_mean/n, load_aug_total/n, load_aug_std/5

def read_pakcet_files(filenames):
	return 0


def main():
	print "Shell we begin?"
	
	time = int(sys.argv[1])
	numFiles = int(sys.argv[2])
	print (time)
	print (numFiles)
	sns.set()
	sns.set(color_codes=True)

	DIR_PATH = '.'
	P1_LOAD_FILES = [	
		os.path.join(DIR_PATH,'load1_%d.csv' % i) for i in range(1, numFiles+1)
		]
	P3_LOAD_FILES = [	
		os.path.join(DIR_PATH,'load3_%d.csv' % i) for i in range(1, numFiles+1)
		]

	P1_PACKET_FILES = [	
		os.path.join(DIR_PATH,'packet1_%d.csv' % i) for i in range(1, numFiles+1)
		]
	P3_PACKET_FILES = [	
		os.path.join(DIR_PATH,'packet3_%d.csv' % i) for i in range(1, numFiles+1)
		]	


	p1_aug_mean, p1_aug_total, p1_aug_std = read_load_files(P1_LOAD_FILES, time)
	p3_aug_mean, p3_aug_total, p3_aug_std = read_load_files(P3_LOAD_FILES, time)


	plt.figure(1, figsize=(10,8))      # the first figure
	plt.subplot(311) 
	x1 = range(1,time+1+9)
	#xnew = np.linspace(min(x), max(x), 1000)
	#smoot3 = spline(x, conv3, xnew) 
	conv3_10 = np.convolve(p3_aug_total, [0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1])
	conv1_10 = np.convolve(p1_aug_total, [0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1])
	plt.plot(x1, conv3_10, label='P3_total_load')
	plt.plot(x1, conv1_10, label='P1_total_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Total Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(conv3_10)+30, ymin = 0)
	plt.legend(loc=1)
	

	plt.subplot(312) 
	x2 = range(1,time+1+4)
	conv3_5 = np.convolve(p3_aug_mean, [0.2,0.2,0.2,0.2,0.2])
	conv1_5 = np.convolve(p1_aug_mean,[0.2,0.2,0.2,0.2,0.2])
	plt.plot(x2, conv3_5, label='P3_mean_load')
	plt.plot(x2, conv1_5, label='P1_mean_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Mean Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(conv3_5)+10, ymin = 0)
	plt.legend(loc=1)

	plt.subplot(313) 
	x3 = range(1,time+1+9) 
	sconv3_10 = np.convolve(p3_aug_std, [0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1])
	sconv1_10 = np.convolve(p1_aug_std, [0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1])
	plt.plot(x3, sconv3_10, label='P3_load_std')
	plt.plot(x3, sconv1_10, label='P1_load_std')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Standard Deviation', fontsize = 12)
	plt.ylim(ymax = max(sconv1_10)+10, ymin = 0)
	plt.legend(loc=1)

	plt.show()

main()
