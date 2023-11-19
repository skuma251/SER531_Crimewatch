import pandas as pd
import numpy as np
import warnings

warnings.filterwarnings('ignore')

shooting_data = 'data/NYPD_Complaint_Data.csv'
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

def preprocess():
	
	complaints_data_df = pd.read_csv(shooting_data)
	
	#Dropping the columns 
	complaints_data_df.drop(columns=['CMPLNT_TO_TM', 'CMPLNT_TO_DT','KY_CD','LAW_CAT_CD','RPT_DT','PD_CD','PD_DESC', 'Lat_Lon','LOC_OF_OCCUR_DESC', 'JURISDICTION_CODE','CRM_ATPT_CPTD_CD','PREM_TYP_DESC','JURIS_DESC', 'PARKS_NM','HADEVELOPT','HOUSING_PSA','TRANSIT_DISTRICT','PATROL_BORO','STATION_NAME'], inplace=True)

	complaints_data_df = complaints_data_df[complaints_data_df['Longitude'].notna()]
	complaints_data_df = complaints_data_df[(complaints_data_df['VIC_AGE_GROUP'] != '(null)')]
	complaints_data_df = complaints_data_df[(complaints_data_df['SUSP_AGE_GROUP'] != 'UNKNOWN')]
	complaints_data_df = complaints_data_df[(complaints_data_df['SUSP_AGE_GROUP'] != '(null)')]
	complaints_data_df = complaints_data_df[(complaints_data_df['VIC_RACE'] != '(null)')]
	complaints_data_df = complaints_data_df[(complaints_data_df['SUSP_RACE'] != '(null)')]

	complaints_data_df = complaints_data_df[(complaints_data_df['OFNS_DESC'] != '(null)')]
	complaints_data_df = complaints_data_df[complaints_data_df['ADDR_PCT_CD'].notna()]

	complaints_data_df = complaints_data_df[complaints_data_df['CMPLNT_NUM'].map(lambda x: len(str(x)) == 9)]
  
	filter_offensetype = complaints_data_df['OFNS_DESC'].isin(offenseType)
	complaints_data_df = complaints_data_df[filter_offensetype]

	complaints_data_df['CMPLNT_DT'] = pd.to_datetime(complaints_data_df['CMPLNT_FR_DT'], errors = 'coerce').dt.strftime('%Y-%m-%d')
	complaints_data_df = complaints_data_df.loc[(complaints_data_df['CMPLNT_DT'] >='2015-01-01')]

	complaints_data_df = complaints_data_df.groupby(['CMPLNT_DT', 'OFNS_DESC']).head(8).reset_index(drop=True)
	
	complaints_data_df['CMPLNT_DATETIME'] = complaints_data_df['CMPLNT_DT'].astype('str') + 'T' + complaints_data_df['CMPLNT_FR_TM'] + 'Z'

	complaints_data_df.rename(columns={'ADDR_PCT_CD': 'Precinct_Id'}, inplace=True)
	complaints_data_df['Precinct_Id'] = complaints_data_df['Precinct_Id'].astype(int) 

	complaints_data_df['Location'] = 'Loc' + complaints_data_df['X_COORD_CD'].astype(str).str[:5] + complaints_data_df['Y_COORD_CD'].astype(str).str[:5]
	complaints_data_df['Data_Source'] = 'Complaints Data'
	complaints_data_df['Boro'] = complaints_data_df['BORO_NM'].apply(get_boro)
	complaints_data_df['Precinct'] = 'Precinct' + complaints_data_df['Precinct_Id'].astype(str)
	complaints_data_df['Crime'] = 'Crime' + complaints_data_df['CMPLNT_NUM'].astype(str)
	complaints_data_df['Suspect'] = 'Perp' + complaints_data_df.reset_index().index.astype(str)
	complaints_data_df['Victim'] = 'Victim' + complaints_data_df.reset_index().index.astype(str)

	complaints_data_df.drop(columns=['X_COORD_CD', 'Y_COORD_CD', 'CMPLNT_FR_DT', 'CMPLNT_FR_TM','CMPLNT_DT'], inplace=True)

	complaints_data_df = complaints_data_df.reindex(columns=['CMPLNT_NUM','CMPLNT_DATETIME','OFNS_DESC','BORO_NM','Precinct_Id','SUSP_AGE_GROUP','SUSP_SEX','SUSP_RACE','VIC_AGE_GROUP','VIC_SEX','VIC_RACE','Latitude','Longitude','Boro','Precinct','Crime','Suspect','Victim','Location','Data_Source'])
	complaints_data_df_split = np.array_split(complaints_data_df, 4)
	complaints_data_df_split[0].to_excel('data/NYPD_Complaints_Data_Processed1.xlsx', index= False)
	complaints_data_df_split[1].to_excel('data/NYPD_Complaints_Data_Processed2.xlsx', index= False)
	complaints_data_df_split[2].to_excel('data/NYPD_Complaints_Data_Processed3.xlsx', index= False)
	complaints_data_df_split[3].to_excel('data/NYPD_Complaints_Data_Processed4.xlsx', index= False)

	print(complaints_data_df_split[0].head(10))
	print(len(complaints_data_df_split[0].index))



if __name__ == '__main__':
    preprocess()