<template>
<div class="container">
  <v-row class="fill-height" >
    <v-col>
      <v-sheet height="64">
        <v-toolbar
          flat
        >
          <v-btn
            v-if="mainCalendar===true"
            outlined
            class="mr-4"
            color="grey darken-2"
            @click="setToday"
          >
            Today
          </v-btn>
          <v-btn
            v-if="mainCalendar===true"
            fab
            text
            small
            color="grey darken-2"
            @click="prev"
          >
            <v-icon small class="fa fa-chevron-left" >
            </v-icon>
          </v-btn>
          <v-btn
            v-if="mainCalendar===true"
            fab
            text
            small
            color="grey darken-2"
            @click="next"
          >
            <v-icon small class="fa fa-chevron-right">
            </v-icon>
          </v-btn>
          <v-toolbar-title v-if="$refs.calendar && mainCalendar===true">
            {{ $refs.calendar.title }}
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-menu
            bottom
            right
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                outlined
                color="grey darken-2"
                v-bind="attrs"
                v-on="on"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon right  class="fa fa-angle-down">                  
                </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day';mainCalendar=true; yearCalendar=false">
                <v-list-item-title>Day</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week';mainCalendar=true; yearCalendar=false">
                <v-list-item-title>Week</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month';mainCalendar=true; yearCalendar=false">
                <v-list-item-title>Month</v-list-item-title>
              </v-list-item>
              <v-list-item @click="mainCalendar=false; yearCalendar=true">
                <v-list-item-title>Year</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600" v-if="mainCalendar===true">
        <v-calendar
          ref="calendar"
          v-model="focus"
          color="primary"
          :events="events"
          :event-color="getEventColor"
          :type="type"
          @click:event="showEvent"
          @click:more="viewDay"
          @click:date="viewDay"
          @change="updateRange"
        ></v-calendar>
        <v-menu
          v-model="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
          offset-x
        >
          <v-card
            color="grey lighten-4"
            min-width="350px"
            flat
          >
            <v-toolbar
              :color="selectedEvent.color"
              dark
            >
              <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>              
              <span v-html="selectedEvent.time1"></span> -
              <span v-html="selectedEvent.time2"></span><br> Status: 
              <span v-html="selectedEvent.status"></span><br>
              <span v-html="selectedEvent.email"></span><br>
              <span v-html="selectedEvent.pharmacyName"></span>
            </v-card-text>
            <v-card-actions>
              <v-btn  text
                color="secondary"
              @click="startAppointment(selectedEvent)"
              v-if="selectedEvent.available === true">
                Start Appointment
              </v-btn>
              <v-btn  text
                color="secondary"
              @click="didntShowUp(selectedEvent)"
              v-if="selectedEvent.available === true">
                Didn't show up
              </v-btn>
              <v-btn
                text
                color="secondary"
                @click="selectedOpen = false"
              >
                Close
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </v-sheet>
      <v-sheet height="600" v-if="yearCalendar===true">
        <Calendar render-style="background"></Calendar>
      </v-sheet>
    </v-col>
  </v-row>
</div>
</template>

<script>
import {client} from '@/client/axiosClient';
import Calendar from '../components/YearCalendar.vue';
  export default {
    name: "WorkCalendar",
    components: {
        Calendar
    },
    data: () => ({
      focus: '',
      type: 'month',
      typeToLabel: {
        month: 'Month',
        week: 'Week',
        day: 'Day',
        year: 'Year',
      },
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      appointments: [],
      mainCalendar: true,
      yearCalendar: false,
    }),
    mounted () {
      this.$refs.calendar.checkChange()
      
    },
    methods: {
      viewDay ({ date }) {
        this.focus = date
        this.type = 'day'
      },
      getEventColor (event) {
        return event.color
      },
      setToday () {
        this.focus = ''
      },
      prev () {
        this.$refs.calendar.prev()
      },
      next () {
        this.$refs.calendar.next()
      },
      showEvent ({ nativeEvent, event }) {
        const open = () => {
          this.selectedEvent = event
          this.selectedElement = nativeEvent.target
          setTimeout(() => {
            this.selectedOpen = true
          }, 10)
        }
        if (this.selectedOpen) {
          this.selectedOpen = false
          setTimeout(open, 10)
        } else {
          open()
        }
        nativeEvent.stopPropagation()
      },
      updateRange ({ start, end }) {
        const events = []
        const min = new Date(`${start.date}`).toISOString();
        const max = new Date(`${end.date}`).toISOString();
        client({
            url: "appointments/allAppointmentsForEmployee",
            params: { employeeUsername : localStorage.getItem('USERNAME'),
                      minDate : min, maxDate : max },
            method: "GET",
            }).then((response) => {
                  this.appointments = response.data;
                  for(var appointment of response.data) {
                      var avail = false;
                      if(appointment.status === 'RESERVED' && (new Date(appointment.timePeriod.startDate) == new Date()) === true){
                        avail = true;
                      } 
                      var patientFullName = appointment.patient.firstName + " " + appointment.patient.lastName;
                      events.push({
                        name: patientFullName,
                        start: appointment.timePeriod.startDate,
                        end: appointment.timePeriod.endDate,
                        color: 'purple lighten-2',
                        email: appointment.patient.email,
                        patientUsername: appointment.patient.username,
                        time1: appointment.timePeriod.startTime,
                        time2: appointment.timePeriod.endTime,
                        pharmacyName : appointment.pharmacy.name,
                        status : appointment.status,
                        available : avail,
                        pharmacy : appointment.pharmacy.id,
                        appointmentId : appointment.id,
                      })
                    
                  }
                  this.events = events
            });
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },

      startAppointment: function(selectedEvent){
        var link = '/appointmentInProgress';
        const encoded = encodeURI(link + '?patientUsername=' + selectedEvent.patientUsername + '&pharmacyId=' + 
        selectedEvent.pharmacy + '&appointmentId=' + selectedEvent.appointmentId);
        this.$router.push(encoded);
      },
      
      didntShowUp: function(selectedEvent){
        client({
          method: 'GET',
          url: 'appointments/patientDidntShowUp',
          params: {appointmentId: selectedEvent.appointmentId}
        })
        this.selectedOpen = false;
        selectedEvent.disabled = true;
        this.$router.go();
      },
    },
  }
</script>