<template>
  <div>
      <!-- Appointments on selected day -->
    <v-row justify="center">
        <v-dialog
        v-model="appointmentsDialog"
        max-width="800px"
        >
        <v-card>
            <v-card-title>
            <span class="headline">Appointments on {{this.selectedDay}}</span>
            </v-card-title>
            <v-card-text>
            <v-container>
                <v-list-item-group
                        color="primary"
                    >
                        <div :key="i" v-for="(appointment, i) in appointmentsOneDay">
                            <v-card
                            class="mx-auto my-12"
                            max-width="800">
                            <v-card-title>
                            <v-col
                            cols="12"
                            sm="7"
                            md="4"
                            >
                            <v-list-item-title>{{appointment.patient.firstName
                                                    }} {{appointment.patient.lastName}}</v-list-item-title>
                            </v-col>
                            <v-col
                            cols="12"
                            sm="6"
                            md="3"
                            >
                            <v-list-item-title >{{
                                                        appointment.timePeriod
                                                            .startTime
                                                    }}
                                                    -
                                                    {{
                                                        appointment.timePeriod
                                                            .endTime
                                                    }}</v-list-item-title>
                            </v-col>
                            <v-col
                            cols="12"
                            sm="6"
                            md="3"
                            >
                            <v-list-item-title >{{
                                                        appointment.status
                                                    }}</v-list-item-title>
                            </v-col>
                            <v-btn plain :right="true" :absolute="true"  @click="showDetails(appointment)">
                                <v-icon color="green">fa fa-info-circle</v-icon>
                            </v-btn>
                            </v-card-title>
                            </v-card>
                        </div>
                    </v-list-item-group>
            </v-container>
            </v-card-text>
            <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                plain
                @click="appointmentsDialog = false"
                color="grey darken-4"
            >
                Close
            </v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
    </v-row>

    <!-- Selected appointment details-->
    <v-row justify="center">
        <v-dialog
        v-model="selectedAppointmentDialog"
        max-width="800px"
        v-if="selectedAppointmentDialog===true"
        >
        <v-card>
            <v-card-title>
            <span class="headline">Appointment on {{this.selectedDay}}</span>
            </v-card-title>
            <v-card-text>
            <v-container>
                <div class="row">
                                <div class="col-sm">
                                    <div class="c1">
                                        <p class="card-text">
                                            Patient:
                                            <b
                                                >{{
                                                    this.selectedAppointment.patient.firstName
                                                }}
                                                {{
                                                    this.selectedAppointment.patient.lastName
                                                }}
                                            </b>
                                        </p>

                                        <p class="card-text">
                                            Patient email:
                                            <b>
                                                {{
                                                    this.selectedAppointment.patient.email
                                                }}
                                            </b>
                                        </p>
                                        <p class="card-text">
                                            Price:
                                            <b>
                                                {{ this.selectedAppointment.price }},00 RSD
                                            </b>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-sm">
                                    <div class="c2">
                                        <p class="card-text">
                                            Pharmacy:
                                            <b>{{
                                                this.selectedAppointment.pharmacy.name
                                            }}</b>
                                        </p>

                                        <p class="card-text">
                                            Time:
                                            <b>
                                                {{
                                                    this.selectedAppointment.timePeriod.startTime
                                                }}
                                                -
                                                {{
                                                    this.selectedAppointment.timePeriod.endTime
                                                }}
                                            </b>
                                        </p>
                                        <p class="card-text">
                                            Status:
                                            <b>
                                                {{ this.selectedAppointment.status }}
                                            </b>
                                        </p>
                                    </div>
                                </div>
                            </div>
            </v-container>
            </v-card-text>
            <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                plain
                color="green"
                @click="startAppointment()"
                v-if="this.selectedAppointment.available === true">
                Start Appointment
            </v-btn>

            <v-btn plain
                color="error"
                @click="didntShowUp()"
                v-if="this.selectedAppointment.available === true">
                Didnt show up
            </v-btn>
            <v-btn
                plain
                @click="selectedAppointmentDialog = false"
                color="grey darken-4"
            >
                Close
            </v-btn>

            </v-card-actions>
        </v-card>
        </v-dialog>
    </v-row>

  </div>
  
</template>

<script>
import {client} from '@/client/axiosClient';
import moment from "moment";
import JsCalendar from 'js-year-calendar';
import 'js-year-calendar/dist/js-year-calendar.css';
export default {
    name: 'Calendar',
    props: [
        'allowOverlap',
        'alwaysHalfDay',
        'contextMenuItems',
        'customDayRenderer',
        'customDataSourceRenderer',
        'dataSource',
        'disabledDays',
        'disabledWeekDays',
        'displayDisabledDataSource',
        'displayHeader',
        'displayWeekNumber',
        'enableContextMenu',
        'enableRangeSelection',
        'hiddenWeekDays',
        'language',
        'loadingTemplate',
        'maxDate',
        'minDate',
        'roundRangeLimits',
        'renderStyle',
        'weekStart',
        'year'
    ],
    data: () => ({
          shouldRender: false,
          appointmentsDialog: false,
          selectedDay: null,
          appointmentsOneDay: [],
          appointmentsOneYear: [],
          selectedAppointment: null,
          selectedAppointmentDialog: false,
          currentYear: new Date().getFullYear()
    }),

    mounted() {
        client({
        url: "appointments/allAppointmentsForEmployee",
        params: { employeeUsername : localStorage.getItem('USERNAME'),
                    minDate : moment(this.currentYear + "-01-01T19:33:38+02:00").format(), maxDate : moment(this.currentYear+"-12-31T19:33:38+02:00").format() },
        method: "GET",
        }).then((response) => {
                var appointments = [];
                var temp = [];
                var index = 0;
                  for(var appointment of response.data) {
                      var patientFullName = appointment.patient.firstName + " " + appointment.patient.lastName;
                      var fullTime = appointment.timePeriod.startTime + "-" + appointment.timePeriod.endTime;
                      var parts1 = appointment.timePeriod.startDate.split('-');
                      var parts2 = appointment.timePeriod.endDate.split('-');
                      var mon1 = parts1[1] - 1;
                      var mon2 = parts2[1] - 1;
                      appointments.push({                        
                        id : index,
                        startDate: new Date(parts1[0], mon1, parts1[2]),
                        endDate: new Date(parts2[0], mon2, parts2[2]),
                        patientName: patientFullName,
                        time: fullTime
                      });
                      index++;
                      
                  }
                  this.dataSource = appointments;
                  this.appointmentsOneYear = response.data;
        })
        this.calendar = new JsCalendar(this.$el, {
            // Options
            allowOverlap: this.allowOverlap,
            alwaysHalfDay: this.alwaysHalfDay,
            contextMenuItems: 
                            [
                            {
                            text: "Update",
                            click: evt => {
                                this.currentId = evt.id;
                                this.currentStartDate = evt.startDate.toISOString().substring(0, 10);
                                this.currentEndDate = evt.endDate.toISOString().substring(0, 10);
                                this.patient = evt.patientName;
                                this.time = evt.time;
                                this.show = true;
                            }
                            },
                            {
                            text: "Delete",
                            click: evt => {
                                this.dataSource = this.dataSource.filter(item => item.id != evt.id);
                            }
                            }
                        ],
            customDayRenderer: this.customDayRenderer,
            customDataSourceRenderer: this.customDataSourceRenderer,
            dataSource: this.dataSource,
            disabledDays: this.disabledDays,
            disabledWeekDays: this.disabledWeekDays,
            displayDisabledDataSource: this.displayDisabledDataSource,
            displayHeader: this.displayHeader,
            displayWeekNumber: this.displayWeekNumber,
            enableContextMenu: this.enableContextMenu,
            enableRangeSelection: this.enableRangeSelection,
            hiddenWeekDays: this.hiddenWeekDays,
            language: this.language,
            loadingTemplate: this.loadingTemplate,
            maxDate: this.maxDate,
            minDate: this.minDate,
            roundRangeLimits: this.roundRangeLimits,
            style: this.renderStyle,
            weekStart: this.weekStart,
            startYear: this.year,
            
            // Events
            clickDay: e => {
                this.$emit('click-day', e); 
                this.selectedDay = moment(e.date).format("MMMM Do yyyy");
                client({
                url: "appointments/allAppointmentsForEmployee",
                params: { employeeUsername : localStorage.getItem('USERNAME'),
                        minDate : moment(e.date).format(), maxDate : moment(e.date).format() },
                method: "GET",
                }).then((response) => {
                    this.appointmentsOneDay = response.data;
                })
                this.appointmentsDialog = true;
            },
            dayContextMenu: e => this.$emit('day-context-menu', e),
            mouseOnDay: e => this.$emit('mouse-on-day', e),
            mouseOutDay: e => this.$emit('mouse-out-day', e),
            renderEnd: e => this.$emit('render-end', e),
            selectRange: e => this.$emit('select-range', e),
            yearChanged: e => {this.$emit('year-changed', e); this.currentYear = e.currentYear;}
        });
    },
    computed: {
        allProps: function() {
            return `
                ${this.allowOverlap}
                ${this.alwaysHalfDay}
                ${this.contextMenuItems}
                ${this.customDayRenderer}
                ${this.customDataSourceRenderer}
                ${this.dataSource}
                ${this.disabledDays}
                ${this.disabledWeekDays}
                ${this.displayDisabledDataSource}
                ${this.displayHeader}
                ${this.displayWeekNumber}
                ${this.enableContextMenu}
                ${this.enableRangeSelection}
                ${this.hiddenWeekDays}
                ${this.language}
                ${this.loadingTemplate}
                ${this.maxDate}
                ${this.minDate}
                ${this.roundRangeLimits}
                ${this.renderStyle}
                ${this.weekStart}
                ${this.year}
            `;
        }
    },
    watch: {
        allowOverlap: function(val) { this.calendar.setAllowOverlap(val); },
        alwaysHalfDay: function(val) { this.calendar.setAlwaysHalfDay(val, true); this.shouldRender = true; },
        contextMenuItems: function(val) { this.calendar.setContextMenuItems(val, true); this.shouldRender = true; },
        customDayRenderer: function(val) { this.calendar.setCustomDayRenderer(val, true); this.shouldRender = true; },
        customDataSourceRenderer: function(val) { this.calendar.setCustomDataSourceRenderer(val, true); this.shouldRender = true; },
        dataSource: function(val) { this.calendar.setDataSource(val, true); this.shouldRender = true; },
        disabledDays: function(val) { this.calendar.setDisabledDays(val, true); this.shouldRender = true; },
        disabledWeekDays: function(val) { this.calendar.setDisabledWeekDays(val, true); this.shouldRender = true; },
        displayDisabledDataSource: function(val) { this.calendar.setDisplayDisabledDataSource(val, true); this.shouldRender = true; },
        displayHeader: function(val) { this.calendar.setDisplayHeader(val, true); this.shouldRender = true; },
        displayWeekNumber: function(val) { this.calendar.setDisplayWeekNumber(val, true); this.shouldRender = true; },
        enableContextMenu: function(val) { this.calendar.setEnableContextMenu(val, true); this.shouldRender = true; },
        enableRangeSelection: function(val) { this.calendar.setEnableRangeSelection(val, true); this.shouldRender = true; },
        hiddenWeekDays: function(val) { this.calendar.setHiddenWeekDays(val, true); this.shouldRender = true; },
        language: function(val) { this.calendar.setLanguage(val, true); this.shouldRender = true; },
        loadingTemplate: function(val) { this.calendar.setLoadingTemplate(val, true); },
        maxDate: function(val) { this.calendar.setMaxDate(val, true); this.shouldRender = true; },
        minDate: function(val) { this.calendar.setMinDate(val, true); this.shouldRender = true; },
        roundRangeLimits: function(val) { this.calendar.setRoundRangeLimits(val, true); this.shouldRender = true; },
        renderStyle: function(val) { this.calendar.setStyle(val, true); this.shouldRender = true; },
        weekStart: function(val) { this.calendar.setWeekStart(val, true); this.shouldRender = true; },
        year: function(val) { this.calendar.setYear(val); },
        allProps: function(val) {
            if (this.shouldRender) {
                this.calendar.render();
                this.shouldRender = false;
            }
        }
    },
    locales: JsCalendar.locales, // Map the "locales" property to the js-year-calendar "locales" property, in order to make the locale files compatible

    methods: {

        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        showDetails: function(appointment){
            var avail = false;
            var parts1 = appointment.timePeriod.startDate.split('-');
            var mon1 = parts1[1]-1;
            if(appointment.status === 'RESERVED' && ((moment(new Date(parts1[0], mon1, parts1[2])).format("MMMM Do yyyy") == moment(new Date()).format("MMMM Do yyyy")))){
                avail = true;
            } 
            this.selectedAppointment = appointment;
            this.selectedAppointment.available = avail;
            this.appointmentsDialog = false;
            this.selectedAppointmentDialog = true;
        },

        startAppointment: function(){
            var link = '/appointmentInProgress';
            const encoded = encodeURI(link + '?patientUsername=' + this.selectedAppointment.patient.username + '&pharmacyId=' + 
                                        this.selectedAppointment.pharmacy.id + '&appointmentId=' + this.selectedAppointment.id);
            this.$router.push(encoded);
        },

         didntShowUp: function(){
            client({
            method: 'GET',
            url: 'appointments/patientDidntShowUp',
            params: {appointmentId: this.selectedAppointment.id}
            })
            this.selectedAppointmentDialog = false;
        },
    }

};
</script>