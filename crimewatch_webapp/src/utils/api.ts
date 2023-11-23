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