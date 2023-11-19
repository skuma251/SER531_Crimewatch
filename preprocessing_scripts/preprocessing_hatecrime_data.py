import pandas as pd
import numpy as np
import warnings

warnings.filterwarnings('ignore')

shooting_data = 'data/NYPD_Hate_Crimes.csv'
offenseType= ['ASSAULT 3 & RELATED OFFENSES','DANGEROUS WEAPONS',
'FELONY ASSAULT','CRIMINAL TRESPASS','HOMICIDE-NEGLIGENT,UNCLASSIFIE','UNLAWFUL POSS. WEAP. ON SCHOOL']

def get_boro(boroname):
    if boroname == 'BRONX':
        return 1
    elif boroname == 'BROOKLYN':
        return 2
    elif boroname == 'MANHATTAN':
        return 3
    elif boroname == 'QUEENS':
        return 4
    elif boroname == 'STATEN ISLAND':
        return 5
    else:
        return 0

def get_borofullname(boroname):
	if boroname == 'BRONX':
		return "BRONX"
	elif boroname == 'BKLYN':
		return "BROOKLYN"
	elif boroname == 'MAN':
		return "MANHATTAN"
	elif boroname == 'QUEENS':
		return "QUEENS"
	elif boroname == 'STATEN':
		return "STATEN ISLAND"
	else:
		return "UNKNOWN"

def preprocess():
	
	hate_crime_data_df = pd.read_csv(shooting_data)
	
	#Dropping the columns 
	hate_crime_data_df.drop(columns=['Arrest Date', 'Arrest Id','Complaint Year Number','Month Number','County','Law Code Category Description','Offense Description','Offense Category'], inplace=True)

	hate_crime_data_df["Boro_Name"] = hate_crime_data_df["Patrol Borough Name"].str.split().str[2:3].str[0].apply(get_borofullname)

	hate_crime_data_df['Record Create Date'] = pd.to_datetime(hate_crime_data_df['Record Create Date']).dt.strftime('%Y-%m-%d')
	hate_crime_data_df['Record_Create_DateTime'] = hate_crime_data_df['Record Create Date'].astype('str') + 'T' + '00:00:00Z'
	hate_crime_data_df['Boro'] = hate_crime_data_df['Boro_Name'].apply(get_boro)
	hate_crime_data_df['Precinct'] = 'Precinct' + hate_crime_data_df['Complaint Precinct Code'].astype(str)
	hate_crime_data_df['Crime'] = 'Crime' + hate_crime_data_df['Full Complaint ID'].astype(str)
	hate_crime_data_df['Suspect'] = 'Perp' + hate_crime_data_df.reset_index().index.astype(str)
	hate_crime_data_df['Victim'] = 'Victim' + hate_crime_data_df.reset_index().index.astype(str)
	hate_crime_data_df['Location'] = 'Location' + hate_crime_data_df.reset_index().index.astype(str)

	hate_crime_data_df.drop(columns=['Record Create Date','Patrol Borough Name'],inplace=True)
	hate_crime_data_df['Data_Source'] = 'Hate Crime Data'

	print(hate_crime_data_df.head(10))
	print(len(hate_crime_data_df.index))

	hate_crime_data_df.to_excel('data/NYPD_Hate_Crime_data_Processed.xlsx', index= False)



if __name__ == '__main__':
    preprocess()