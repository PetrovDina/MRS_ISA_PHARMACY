<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer>
                    <label>Graphic</label>
                    <select class="sort-dropdown" @change="graphicSelected">
                        <option v-for="graphic in graphics" :key="graphic" :value="graphic">{{graphic}}</option>
                    </select>
                </v-spacer>
                <div>
                    <form>
                        <label>Month</label>
                        <select id="month" class="sort-dropdown" @change="monthSelected">
                            <option v-for="month in months" :key="month" :value="month">{{month}}</option>
                        </select>
                        <label>Quarter</label>
                        <select id="quarter" class="sort-dropdown" @change="quarterSelected">
                            <option v-for="quarter in quarters" :key="quarter" :value="quarter">{{quarter}}</option>
                        </select>
                        <label>Year</label>
                        <select id="year" class="sort-dropdown" @change="yearSelected">
                            <option v-for="year in years" :key="year" :value="year">{{year}}</option>
                        </select>
                        <div class="sort-dropdown">
                            <input type="checkbox" value="" id="checkBox" @change="yearlySelected">
                            <label class="form-check-label" for="checkBox">
                                Yearly
                            </label>
                        </div>
                    </form>
                </div>
                <v-spacer>
                <Button
                    @action-performed="report()"
                    class="btn" 
                    text="Report" 
                    bgd_color="rgba(15, 95, 72, 0.85)" 
                    :style="{color : 'rgba(255,255,255, 0.9)'}">
                </Button>
                </v-spacer>
            </v-card-title>
        </v-card>
        <fusioncharts v-if="noSignificantData"
            type='column2d'
            width='90%'
            height='400'
            dataFormat='json'
            :dataSource="dataSource"
        ></fusioncharts>
        <h3 v-if="!noSignificantData">There are no significant data to display! Try another period.</h3>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Vue from 'vue'
import VueFusionCharts from 'vue-fusioncharts';

import FusionCharts from 'fusioncharts';
import Widgets from 'fusioncharts/fusioncharts.widgets';
import Charts from 'fusioncharts/fusioncharts.charts';
import Theme from 'fusioncharts/themes/fusioncharts.theme.fusion';
import Button from '../components/Button.vue';
import LoginPage from './LoginPage.vue';

Vue.use(VueFusionCharts, FusionCharts, Charts, Widgets, Theme);

export default {
    name : "PharmacyAdminReportView",
    components : { VueFusionCharts, FusionCharts, Charts, Button, LoginPage},
    data(){
        return {
            selectedQuarter : "-select-quarter-",
            quarters : ["-select-quarter-", "Q1", "Q2", "Q3", "Q4"],
            selectedYear : "-select-year-",
            years : ["-select-year-", "2021", "2020"],
            wholeYear : false,
            selectedMonth : "-select-month-",
            months : ["-select-month-", "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"],
            selectedGraphic : "-----select-graphic-----",
            graphics : ["-----select-graphic-----", "Concluded appointments", "Medication consumption", "Pharmacy revenue"],
            noSignificantData : false,
            pharmacyId : -1,
            dataSource : {
                chart: {
                    caption: "Concluded examinations",
                    //"subCaption": "In MMbbl = One Million barrels",
                    xAxisName: "Days",
                    yAxisName: "Num. of concluded appointments",
                    //"numberSuffix": "K", Kao hiljade
                    "theme": "fusion"
                },
                data : []
            }
        }
    },
    mounted(){
        if(this.pharmacyId == -1){
            client({
                url: "pharmacyAdmin/" + localStorage.getItem('USERNAME'),
                method: "GET",
            }).then((response) => {
                this.pharmacyId = response.data.pharmacyId;
            });
        }
    },
    methods : {
        sortByMonth : function(arr) {
            var months = ["January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"];
            arr.sort(function(a, b){
                return months.indexOf(a.label)
                    - months.indexOf(b.label);
            });
        },
        reportAppointments : function(){
            this.dataSource.chart.yAxisName = "Num. of concluded appointments";
            if(this.selectedQuarter != this.quarters[0])
                client({
                        url: "appointments/reportAppointmentQuarter",
                        method: "GET",
                        params: {
                            quarter: this.selectedQuarter,
                            year: this.selectedYear,
                            pharmacyId : this.pharmacyId
                        },
                    }).then((response) => {
                        this.makeDataForGraphic(response.data.data);
                        this.sortByMonth(this.dataSource.data);
                        this.dataSource.chart.xAxisName = "Months";
                        this.dataSource.chart.caption = "Concluded appointments - " + this.selectedQuarter;
                        this.defaultYear();
                    });
            else if(this.selectedMonth != this.months[0])
                client({
                        url: "appointments/reportAppointmentMonth",
                        method: "GET",
                        params: {
                            period: this.selectedMonth,
                            year: this.selectedYear,
                            pharmacyId : this.pharmacyId
                        },
                    }).then((response) => {
                        this.makeDataForGraphic(response.data.data);
                        this.dataSource.chart.xAxisName = "Days";
                        this.dataSource.chart.caption = "Concluded appointments - " + this.selectedMonth;
                        this.defaultYear();
                    });
            else
                client({
                        url: "appointments/reportAppointmentYear",
                        method: "GET",
                        params: {
                            year: this.selectedYear,
                            pharmacyId : this.pharmacyId
                        },
                    }).then((response) => {
                        this.makeDataForGraphic(response.data.data);
                        this.sortByMonth(this.dataSource.data)
                        this.dataSource.chart.xAxisName = "Months";
                        this.dataSource.chart.caption = "Concluded appointments - " + this.selectedYear;
                        this.defaultYear();
                    });
        },
        reportMedicationConsumpsion : function(){
            this.dataSource.chart.yAxisName = "Num. of consumpted medication";
            if(this.selectedQuarter != this.quarters[0])
                client({
                        url: "reservation/reportMedicationConsumptionQuarter",
                        method: "GET",
                        params: {
                            quarter: this.selectedQuarter,
                            year: this.selectedYear,
                            pharmacyId : this.pharmacyId
                        },
                    }).then((response) => {
                        this.makeDataForGraphic(response.data.data);
                        this.sortByMonth(this.dataSource.data);
                        this.dataSource.chart.xAxisName = "Months";
                        this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedQuarter;
                        this.defaultYear();
                    });
            else if(this.selectedMonth != this.months[0])
                client({
                    url: "reservation/reportMedicationConsumptionMonth",
                    method: "GET",
                    params: {
                        period: this.selectedMonth,
                        year: this.selectedYear,
                        pharmacyId : this.pharmacyId
                    },
                }).then((response) => {
                    this.makeDataForGraphic(response.data.data);
                    this.dataSource.chart.xAxisName = "Days";
                    this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedMonth;
                    this.defaultYear();
                });
            else
                client({
                    url: "reservation/reportMedicationConsumptionYear",
                    method: "GET",
                    params: {
                        year: this.selectedYear,
                        pharmacyId : this.pharmacyId
                    },
                }).then((response) => {
                    this.makeDataForGraphic(response.data.data);
                    this.sortByMonth(this.dataSource.data)
                    this.dataSource.chart.xAxisName = "Months";
                    this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedYear;
                    this.defaultYear();
                });
        },
        reportPharmacyRevenue : function(){
            this.dataSource.chart.yAxisName = "RSD";
            if(this.selectedQuarter != this.quarters[0])
                client({
                        url: "pharmacy/reportPharmacyRevenueQuarter",
                        method: "GET",
                        params: {
                            quarter: this.selectedQuarter,
                            year: this.selectedYear,
                            pharmacyId : this.pharmacyId
                        },
                    }).then((response) => {
                        this.makeDataForGraphic(response.data.data);
                        this.sortByMonth(this.dataSource.data);
                        this.dataSource.chart.xAxisName = "Months";
                        this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedQuarter;
                        this.defaultYear();
                    });
            else if(this.selectedMonth != this.months[0])
                client({
                    url: "pharmacy/reportPharmacyRevenueMonth",
                    method: "GET",
                    params: {
                        period: this.selectedMonth,
                        year: this.selectedYear,
                        pharmacyId : this.pharmacyId
                    },
                }).then((response) => {
                    this.makeDataForGraphic(response.data.data);
                    this.dataSource.chart.xAxisName = "Days";
                    this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedMonth;
                    this.defaultYear();
                });
            else
                client({
                    url: "pharmacy/reportPharmacyRevenueYear",
                    method: "GET",
                    params: {
                        year: this.selectedYear,
                        pharmacyId : this.pharmacyId
                    },
                }).then((response) => {
                    this.makeDataForGraphic(response.data.data);
                    this.sortByMonth(this.dataSource.data)
                    this.dataSource.chart.xAxisName = "Months";
                    this.dataSource.chart.caption = this.selectedGraphic + " - " + this.selectedYear;
                    this.defaultYear();
                });
        },
        report : function(){
            if(this.selectedGraphic === this.graphics[0]){
                this.$toasted.show("Please select graphic!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
            }
            else {
                if(this.wholeYear){
                    if(this.selectedYear === this.years[0]){
                        this.$toasted.show("Please select year!", {
                            theme: "toasted-primary",
                            position: "top-center",
                            duration: 2000,
                        });
                        return;
                    }
                }
                else
                {
                    if(this.selectedYear === this.years[0]){
                        this.$toasted.show("Please select year!", {
                            theme: "toasted-primary",
                            position: "top-center",
                            duration: 2000,
                        });
                        return;
                    }
                    if(this.selectedMonth === this.months[0] && this.selectedQuarter === this.quarters[0]){
                        this.$toasted.show("Please select month or quarter!", {
                            theme: "toasted-primary",
                            position: "top-center",
                            duration: 1000,
                        });
                        return;
                    }
                }
                
                if(this.selectedGraphic === this.graphics[1])
                    this.reportAppointments();
                else if(this.selectedGraphic === this.graphics[2])
                    this.reportMedicationConsumpsion();
                else
                    this.reportPharmacyRevenue();
            }
        },
        makeDataForGraphic : function(data){
            this.dataSource.data = [];
            this.noSignificantData = false;
            for(const label in data) {
                const value = data[label];
                this.dataSource.data.push({
                    'label' : label,
                    'value' : value
                    });
                
                if(!this.noSignificantData && value != 0)
                    this.noSignificantData = true;
            }
        },
        defaultYear : function(){
            if(this.selectedYear != this.years[0]){
                this.selectedYear = this.years[0];
                document.getElementById("year").value = this.years[0];
                document.getElementById("checkBox").checked = false;
            }
        },
        defaultMonth : function(){
            if(this.selectedMonth != this.months[0]){
                this.selectedMonth = this.months[0];
                document.getElementById("month").value = this.months[0];
            }
        },
        defaultQuarter : function(){
            if(this.selectedQuarter != this.quarters[0]){
                this.selectedQuarter = this.quarters[0];
                document.getElementById("quarter").value = this.quarters[0];
            }
        },
        monthSelected : function(event){
            this.selectedMonth = event.target.value;
            this.defaultQuarter();
        },
        quarterSelected : function(event){
            this.selectedQuarter = event.target.value;
            this.defaultMonth();
        },
        yearSelected : function(event){
            this.selectedYear = event.target.value;
        },
        graphicSelected : function(event){
            this.selectedGraphic = event.target.value;
        },
        yearlySelected : function(event){
            this.wholeYear = event.target.checked;
            if(this.wholeYear){
                this.defaultMonth();
                this.defaultQuarter();
            }
        }
    }
}
</script>



<style scoped>
.sort-dropdown {
    padding: 5px;
    margin: 5px 5px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    border-radius: 5px;
    display: inline-block;
}
</style>