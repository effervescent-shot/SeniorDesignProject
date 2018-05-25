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
	#print time

	x = np.linspace(0, time)
	
	links3 = path3load['Links:']
	path3T = path3load.iloc[:, range(1,time+1)]
	path3_mean_t = path3T.mean(axis=0)
	path3_std_t = path3T.std(axis=0)

	links1 = path1load['Links:']
	path1T = path1load.iloc[:, range(1,time+1)]
	path1_mean_t = path1T.mean(axis=0)
	path1_std_t = path1T.std(axis=0)


	plt.figure(1, figsize=(10,10)) 
	plt.subplot(211)
	sns.set()
	plt.plot(x, path3_mean_t, 'r+', label='path3_load_mean')
	#plt.plot(x, path1_mean_t, 'bo', label='path1_load_mean' )
	plt.show()
	print(len(x))
	print(len(path3_mean_t))



main()