import axios from 'axios';
import { NewsArticle } from './types';

const API_KEY = '5e963bbd56ee4c8e9eac6e2f19a865da';
const NEWS_URL = 'https://newsapi.org/v2/';

export async function fetchNews(): Promise<NewsArticle[]> {
  const sevenDaysAgo = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);
  const formattedDate = sevenDaysAgo.toISOString().split('T')[0];
  const endpoint = `${NEWS_URL}everything?q=newyork crime&from=${formattedDate}&sortBy=publishedAt&apiKey=${API_KEY}`;
  try {
    const response = await axios.get(endpoint);
    const articles: NewsArticle[] = response.data.articles.slice(0, 10);
    return articles;
  } catch (error) {
    console.error('Error fetching Tesla news:', error);
    return [];
  }
}

const URL = "http://localhost:8082"

export async function getYears(): Promise<number[]> {
  try {
    const response = await axios.get<number[]>(`${URL}/complaints/getYears`);
    return response.data;
    
  } catch (error) {
    console.error('Error fetching years:', error);
    return [];
  }
}


export async function getCrimeTypeCountByYear(year: number, sex: String): Promise<{ [key: string]: number }> {
  let sexChar = "U"
  if ( sex === "Male") {
    sexChar = "M"
  }
  else {
    sexChar = "F"
  }
  try {
    const response = await axios.get<{ [key: string]: number }>(`${URL}/complaints/getCrimeTypeCountByYear?year=${year}&sex=${sexChar}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching crime type count for year ${year}:`, error);
    return {};
  }
}

export async function getBoroCrimeCountByYear(year: number): Promise<{ [key: string]: number }>  {

  try {
    const response = await axios.get<{ [key: string]: number }>(`${URL}/complaints/getBoroCrimeCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching crime type count for year ${year}:`, error);
    return {};
  }
}

export async function getBoroMonthlyStatsByYear(year: number): Promise<{ [key: string]: number }>  {
  try {
    const response = await axios.get<{  [key: string]: number } >(`${URL}/complaints/getBoroMonthlyStats?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching borough-wise monthly stats for year ${year}:`, error);
    return {};
  }
}

export async function getYearsShooting(): Promise<number[]> {
  try {
    const response = await axios.get<number[]>(`${URL}/shooting/getYears`);
    return response.data;
    
  } catch (error) {
    console.error('Error fetching years for shooting data:', error);
    return [];
  }
}

export async function getVictimCountByRace(year: number, boro:String): Promise<{ [key: string]:  number  }> {
  try {
    const response = await axios.get<{  [key: string]: number } >(`${URL}/shooting/getVictimCountByBoro?year=${year}&boro=${boro}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching victim count for year ${year} and boro ${boro}:`, error);
    return {};
  }
}

export async function getPerpCountByRace(year: number, boro:String): Promise<{ [key: string]:  number  }> {
  try {
    const response = await axios.get<{ [key: string]: number }>(`${URL}/shooting/getPerpCountByBoro?year=${year}&boro=${boro}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching perpetrator count for year ${year} and boro ${boro}:`, error);
    return {};
  }
}

export async function getCountByBoro(year: number): Promise<{ [key: string]:  number  }> {
  try {
    const response = await axios.get<{ [key: string]: number  }>(`${URL}/shooting/getBoroCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching perpetrator count for year ${year}:`, error);
    return {};
  }
}

export async function getYearsHateCrime(): Promise<number[]> {
  try {
    const response = await axios.get<number[]>(`${URL}/hatecrime/getYears`);
    return response.data;
    
  } catch (error) {
    console.error('Error fetching years for shooting data:', error);
    return [];
  }
}

export async function getHateCrimeCountByBoro(year: number): Promise<{ [key: string]:  number  }> {
  try {
    const response = await axios.get<{ [key: string]: number  }>(`${URL}/hatecrime/getBoroCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching perpetrator count for year ${year}:`, error);
    return {};
  }
}

export async function getBiasCountByYear(year: number): Promise<{ [key: string]: number}> {
  try {
    const response = await axios.get<{ [key: string]:  number  }>(`${URL}/hatecrime/getBiasCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching perpetrator count for year ${year}:`, error);
    return {};
  }
}

export async function getShootingHateCrimeCountByYear(year: number): Promise<{ [key: string]: number }> {
  try {
    const response = await axios.get<{ [key: string]: number }>(`${URL}/miscellaneous/getComplaintShootingCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching perpetrator count for year ${year}:`, error);
    return {};
  }
}


