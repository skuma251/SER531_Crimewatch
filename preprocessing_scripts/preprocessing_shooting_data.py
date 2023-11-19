import pandas as pd
import numpy as np

shooting_data = 'data/NYPD_Shooting_Incident_Data.csv'
offensetype = ['Individual Targeted Shooting', 'Public Place Shooting', 'Private Property Shooting', 'Group Targeted Shooting']

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

def preprocess():
	
	shooting_data_df = pd.read_csv(shooting_data)
	
	#Dropping the columns 
	shooting_data_df = shooting_data_df.drop(columns=['LOC_OF_OCCUR_DESC', 'LOC_CLASSFCTN_DESC', 'Lon_Lat', 'JURISDICTION_CODE', 'STATISTICAL_MURDER_FLAG','LOCATION_DESC'])
	shooting_data_df['OCCUR_DATE_F'] = pd.to_datetime(shooting_data_df['OCCUR_DATE']).dt.strftime('%Y-%m-%d')
	#shooting_data_df = shooting_data_df.loc[(shooting_data_df['OCCUR_DATE_F'] >='2015-01-01')]
	shooting_data_df['OCCUR_DATETIME'] = shooting_data_df['OCCUR_DATE_F'].astype('str') + 'T' + shooting_data_df['OCCUR_TIME'] + 'Z'
	#Removing the age column having null values
	shooting_data_df = shooting_data_df[shooting_data_df['PERP_AGE_GROUP'].notna()]
	shooting_data_df = shooting_data_df[(shooting_data_df['PERP_AGE_GROUP'] != 'UNKNOWN')]
	shooting_data_df = shooting_data_df[(shooting_data_df['PERP_AGE_GROUP'] != '(null)')]
	shooting_data_df = shooting_data_df[(shooting_data_df['PERP_AGE_GROUP'] != '940')]
	shooting_data_df = shooting_data_df[(shooting_data_df['PERP_AGE_GROUP'] != '224')]
	shooting_data_df = shooting_data_df[(shooting_data_df['PERP_AGE_GROUP'] != '1020')]
	shooting_data_df = shooting_data_df[(shooting_data_df['VIC_AGE_GROUP'] != 'UNKNOWN')]
	shooting_data_df.rename(columns={'BORO': 'Boro_Name'}, inplace=True)
	shooting_data_df.rename(columns={'PRECINCT': 'Precinct_Id'}, inplace=True)
	shooting_data_df['Boro'] = shooting_data_df['Boro_Name'].apply(get_boro)
	shooting_data_df['Precinct'] = 'Precinct' + shooting_data_df['Precinct_Id'].astype(str)
	shooting_data_df['Crime'] = 'Crime' + shooting_data_df['INCIDENT_KEY'].astype(str)
	shooting_data_df['Perperator'] = 'Perp' + shooting_data_df.reset_index().index.astype(str)
	shooting_data_df['Victim'] = 'Victim' + shooting_data_df.reset_index().index.astype(str)
	shooting_data_df['Location'] = 'Loc' + shooting_data_df['X_COORD_CD'].astype(str).str[:5] + shooting_data_df['Y_COORD_CD'].astype(str).str[:5]
	shooting_data_df['Data_Source'] = 'Shooting Data'
	shooting_data_df["Offense_Type"] = np.random.choice(offensetype, size=len(shooting_data_df))
	shooting_data_df.drop(columns=['X_COORD_CD', 'Y_COORD_CD', 'OCCUR_DATE_F', 'OCCUR_DATE','OCCUR_TIME'], inplace=True)
	shooting_data_df = shooting_data_df.reindex(columns=['INCIDENT_KEY','OCCUR_DATETIME','Offense_Type','Boro_Name','Precinct_Id','PERP_AGE_GROUP','PERP_SEX','PERP_RACE','VIC_AGE_GROUP','VIC_SEX','VIC_RACE','Latitude','Longitude','Boro','Precinct','Crime','Perperator','Victim','Location','Data_Source'])
	shooting_data_df.to_excel('data/NYPD_Shooting_Incident_Data_Processed.xlsx', index= False)
	print(shooting_data_df.head(10))
	print(len(shooting_data_df.index))



if __name__ == '__main__':
    preprocess()