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
                color="green"
              @click="startAppointment(selectedEvent)"
              v-if="selectedEvent.available === true">
                Start Appointment
              </v-btn>
              <v-btn  text
                color="error"
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

  <v-snackbar
      v-model="snackbar"
      :vertical="vertical"
      light
      timeout="2000"
    >
      {{snackbarText}}

      <template v-slot:action="{ attrs }">
        <v-btn
          color="indigo"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
</div>
</template>

<script>
import {client} from '@/client/axiosClient';
import moment from "moment";
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
      snackbar: false,
      snackbarText: null,
      vertical: false,
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
                      var parts1 = appointment.timePeriod.startDate.split('-');
                      var mon1 = parts1[1]-1; 
                      var d = new Date(`${appointment.timePeriod.startDate}T${appointment.timePeriod.startTime}:00`)
                      var d2 = new Date(`${appointment.timePeriod.endDate}T${appointment.timePeriod.endTime}:00`)
                      if(appointment.status === 'RESERVED' && !appointment.inProgress &&
                       ((moment(new Date(parts1[0], mon1, parts1[2])).format("MMMM Do yyyy") == moment(new Date()).format("MMMM Do yyyy")))){
                        avail = true;
                      } 
                      var patientFullName = appointment.patient.firstName + " " + appointment.patient.lastName;
                      events.push({
                        name: patientFullName,
                        start: d,//appointment.timePeriod.startDate,
                        end: d2,//appointment.timePeriod.endDate,
                        color: 'purple lighten-2',
                        timed: true,
                        email: appointment.patient.email,
                        patientUsername: appointment.patient.username,
                        time1: appointment.timePeriod.startTime,
                        time2: appointment.timePeriod.endTime,
                        pharmacyName : appointment.pharmacy.name,
                        status : appointment.status,
                        available : avail,
                        pharmacy : appointment.pharmacy.id,
                        appointmentId : appointment.id,
                        inProgress : appointment.inProgress,
                      })
                    
                  }
                  this.events = events
            });
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },

      startAppointment: function(selectedEvent){
          client({
            method: 'GET',
            url: 'appointments/setAppointmentInProgress',
            params: {appointmentId: selectedEvent.appointmentId}
          })
          .then((response) => {
                if(response.data == "ok"){
                    var link = '/appointmentInProgress';
                    const encoded = encodeURI(link + '?patientUsername=' + selectedEvent.patientUsername + '&pharmacyId=' + 
                    selectedEvent.pharmacy + '&appointmentId=' + selectedEvent.appointmentId);
                    this.$router.push(encoded);
                }else{
                    this.snackbarText = response.data;
                    this.snackbar = true;
                }
            })      
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
<!--<template>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="64">
        <v-toolbar
          flat
        >
          <v-btn
            outlined
            class="mr-4"
            color="grey darken-2"
            @click="setToday"
          >
            Today
          </v-btn>
          <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="prev"
          >
            <v-icon small>
              mdi-chevron-left
            </v-icon>
          </v-btn>
          <v-btn
            fab
            text
            small
            color="grey darken-2"
            @click="next"
          >
            <v-icon small>
              mdi-chevron-right
            </v-icon>
          </v-btn>
          <v-toolbar-title v-if="$refs.calendar">
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
                <v-icon right>
                  mdi-menu-down
                </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day'">
                <v-list-item-title>Day</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week'">
                <v-list-item-title>Week</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month'">
                <v-list-item-title>Month</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = '4day'">
                <v-list-item-title>4 days</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
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
              <v-btn icon>
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
              <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn icon>
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <v-btn icon>
                <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </v-toolbar>
            <v-card-text>
              <span v-html="selectedEvent.details"></span>
            </v-card-text>
            <v-card-actions>
              <v-btn
                text
                color="secondary"
                @click="selectedOpen = false"
              >
                Cancel
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </v-sheet>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    data: () => ({
      focus: '',
      type: 'month',
      typeToLabel: {
        month: 'Month',
        week: 'Week',
        day: 'Day',
        '4day': '4 Days',
      },
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
      names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party'],
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
          requestAnimationFrame(() => requestAnimationFrame(() => this.selectedOpen = true))
        }
        if (this.selectedOpen) {
          this.selectedOpen = false
          requestAnimationFrame(() => requestAnimationFrame(() => open()))
        } else {
          open()
        }
        nativeEvent.stopPropagation()
      },
      updateRange ({ start, end }) {
        const events = []
        const min = new Date(`${start.date}T00:00:00`)
        const max = new Date(`${end.date}T23:59:59`)
        const days = (max.getTime() - min.getTime()) / 86400000
        const eventCount = this.rnd(days, days + 20)
        for (let i = 0; i < eventCount; i++) {
          const allDay = this.rnd(0, 3) === 0
          const firstTimestamp = this.rnd(min.getTime(), max.getTime())
          const first = new Date(firstTimestamp - (firstTimestamp % 900000))
          console.log("first: " +first );
          const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
          const second = new Date(first.getTime() + secondTimestamp)
          console.log("second: " +second );
          events.push({
            name: this.names[this.rnd(0, this.names.length - 1)],
            start: first,
            end: second,
            color: this.colors[this.rnd(0, this.colors.length - 1)],
            timed: !allDay,
          })
        }
        this.events = events
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },
    },
  }
</script>-->