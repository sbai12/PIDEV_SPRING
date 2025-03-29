import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { AdminService } from 'src/app/services/admin.service';

Chart.register(...registerables);

@Component({
  selector: 'app-admin-statistics',
  templateUrl: './admin-statistics.component.html',
  styleUrls: ['./admin-statistics.component.css']
})
export class AdminStatisticsComponent implements OnInit {
  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadChartData();
  }

  loadChartData(): void {
    this.adminService.getCommentsPerPost().subscribe(data => {
      // Assuming the service returns an array of tuples [postId, commentCount]
      const postIds = data.map((item: any) => item[0]); // Post IDs
      const commentCounts = data.map((item: any) => item[1]); // Comment counts

      new Chart("barChart", {
        type: 'bar',
        data: {
          labels: postIds,
          datasets: [{
            label: 'Nombre de commentaires',
            data: commentCounts,
            backgroundColor: 'rgba(255, 4, 4, 0.64)',
            borderColor: 'rgb(255, 255, 255)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    });
  }
}
