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

	return load_aug_mean/n, load_aug_total/n, load_aug_std/n

def read_pakcet_files(filenames):
	packet_aug_mean = []
	#print(filenames)
	for file in filenames:
		p_load = pd.read_csv(file, sep=',', header=None)
		p_mean = p_load.mean(axis=1)
		packet_aug_mean.append(p_mean)
		#print(p_mean)
	return packet_aug_mean


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
	
	#p1_packet_means = read_pakcet_files(P1_PACKET_FILES)
	#p3_packet_means = read_pakcet_files(P3_PACKET_FILES)
	p1_packet_means = pd.read_csv('packet1.csv', sep=',', header=None)
	p3_packet_means = pd.read_csv('packet3.csv', sep=',', header=None)



	plt.figure(1, figsize=(10,8))      # the first figure
	plt.subplot(311) 
	x1 = range(101,time-69+1+19)
	conv3_20 = np.convolve(p3_aug_total, np.full ((20),0.05))
	conv1_20 = np.convolve(p1_aug_total, np.full ((20),0.05))
	plt.plot(x1, conv3_20[100:950], label='P3_total_load')
	plt.plot(x1, conv1_20[100:950], label='P1_total_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Total Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(conv3_20)+100, ymin = 0)
	plt.legend(loc=4)
	

	plt.subplot(312) 
	x2 = range(101,time-69+1+19)
	mconv3_20 = np.convolve(p3_aug_mean,np.full ((20),0.05))
	mconv1_20 = np.convolve(p1_aug_mean,np.full ((20),0.05))
	plt.plot(x2, mconv3_20[100:950], label='P3_mean_load')
	plt.plot(x2, mconv1_20[100:950], label='P1_mean_load')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Mean Load(bps)', fontsize = 12)
	plt.ylim(ymax = max(mconv3_20)+50, ymin = 0)
	plt.legend(loc=4)

	plt.subplot(313) 
	x3 = range(101,time-69+1+19) 
	sconv3_20 = np.convolve(p3_aug_std,np.full ((20),0.05))
	sconv1_20 = np.convolve(p1_aug_std,np.full ((20),0.05))
	plt.plot(x3, sconv3_20[100:950], label='P3_load_std')
	plt.plot(x3, sconv1_20[100:950], label='P1_load_std')
	plt.xlabel('Time(sec)' , fontsize = 12)
	plt.ylabel('Standard Deviation', fontsize = 12)
	plt.ylim(ymax = max(sconv1_20)+50, ymin = 0)
	plt.legend(loc=4)

	plt.figure(2, figsize=(8,8))
	#sns.boxplot(data = [ np.asarray(p3_packet_means) , np.asarray(p1_packet_means)])
	sns.boxplot(data = [ p3_packet_means[100:] , p1_packet_means[100:]])
	plt.xlabel('Path Order' , fontsize = 12)
	plt.ylabel('Avg Delivery Time(msec)', fontsize = 12)
	plt.show()

main()
