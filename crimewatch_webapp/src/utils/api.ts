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

export async function getBoroCrimeCountByYear(year: number): Promise<{ [key: string]: number }> {

  try {
    const response = await axios.get<{ [key: string]: number }>(`${URL}/complaints/getBoroCrimeCountByYear?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching crime type count for year ${year}:`, error);
    return {};
  }
}

export async function getBoroMonthlyStatsByYear(year: number): Promise<{ [key: string]: { [key: string]: number } }> {
  try {
    const response = await axios.get<{ [key: string]: { [key: string]: number } }>(`${URL}/complaints/getBoroMonthlyStats?year=${year}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching borough-wise monthly stats for year ${year}:`, error);
    return {};
  }
}


