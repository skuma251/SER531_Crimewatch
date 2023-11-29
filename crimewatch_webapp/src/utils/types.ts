  export interface NewsArticle {
    title: string;
    description: string;
    author: string;
  }


export interface BarChartData {
  labels: string[];
  datasets: {
    label: string;
    data: number[];
    backgroundColor: string[];
    borderColor: string[];
    borderWidth?: number;
  }[];
}

interface PieChartDataset {
  label: string;
  data: number[];
  backgroundColor: string[];
  hoverOffset: number;
}

export interface PieChartData {
  labels: string[];
  datasets: PieChartDataset[];
}

export interface LineChartData {
  labels: string[];
  datasets: {
    label: string;
    data: number[];
    fill?: boolean;
    borderColor: string;
    tension?: number;
  }[];
}

interface PolarAreaChartDatasets{
  label: string;
  data: number[];
  backgroundColor: string[];
}
export interface PolarAreaChart{
  labels: string[];
  datasets: PolarAreaChartDatasets[];
}

export type WordEntry = [string:  number];
