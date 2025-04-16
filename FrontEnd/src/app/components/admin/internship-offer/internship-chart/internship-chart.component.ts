import { Component, OnInit, Input, OnChanges, SimpleChanges, AfterViewInit } from '@angular/core';
import { Chart, ChartType, registerables } from 'chart.js';
import axios from 'axios';

Chart.register(...registerables);

interface InternshipData {
  companyName: string;
  internshipCount: number;
}

@Component({
  selector: 'app-internship-chart',
  templateUrl: './internship-chart.component.html',
  styleUrls: ['./internship-chart.component.css']
})
export class InternshipChartComponent implements OnInit, OnChanges {
  @Input() isVisible: boolean = false;
  chart: any;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isVisible'] && this.isVisible) {
      setTimeout(() => {
        this.fetchChartData();
      }, 300); // Give time for the modal to fully render
    }
  }

  fetchChartData() {
    axios.get<InternshipData[]>('http://localhost:8083/companies/internship-counts')
      .then((response) => {
        const companyNames = response.data.map(c => c.companyName);
        const internshipCounts = response.data.map(c => c.internshipCount);
  
        this.createChart(companyNames, internshipCounts);
      })
      .catch(error => console.error("Error fetching chart data:", error));
  }

  createChart(companyNames: string[], internshipCounts: number[]) {
    const chartCanvas = document.getElementById("internshipChart") as HTMLCanvasElement;
    
    if (!chartCanvas) {
      console.error("Canvas element not found! Retrying...");
      setTimeout(() => this.createChart(companyNames, internshipCounts), 300); // Retry after delay
      return;
    }

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart(chartCanvas, {
      type: 'bar' as ChartType,
      data: {
        labels: companyNames,
        datasets: [{
          label: 'Internships Per Company',
          data: internshipCounts,
          backgroundColor: 'rgba(54, 162, 235, 0.6)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  closeModal() {
    this.isVisible = false;
  }
}
