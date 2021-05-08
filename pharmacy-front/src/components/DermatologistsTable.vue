<template>
    <div id="dermos">
        <table class="table table-hover" v-closable="{exclude: [], handler: 'onOutOfFocus'}">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Gender</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Work time</th>
                    <th scope="col">Appointment</th>
                </tr>
            </thead>

            <tbody>
                <tr  :key="der.id" v-for="der in dermatologists" @click="clickedOnRow(der)" v-bind:class="{selected : selected_dermatologist.id===der.id}">
                    <td>{{der.id}}</td>
                    <td>{{der.username}}</td>
                    <td>{{der.email}}</td>
                    <td>{{der.firstName}}</td>
                    <td>{{der.lastName}}</td>
                    <td><i v-bind:class="der.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                    <td>{{der.rating}}</td>
                    <td>{{der.workTime.startTime}} - {{der.workTime.endTime}}</td>
                    <td><Button @action-performed="clickedId(der.id)" class="btn" text="New" bgd_color="rgba(15, 95, 72, 0.85)" :style="{color : 'rgba(255,255,255, 0.9)'}"></Button></td>
                </tr>
            </tbody>
        </table>
        <Button
            @action-performed="hireDermatologist()"
            class="btn" 
            text="Hire dermatologist" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
        </Button>
        <ModalWindowHireDermatologist
            @hired-dermatologist = "hiredDermatologist"
            @modal-closed = "mw_show_dermatologist = false" 
            :modal_show = "mw_show_dermatologist"
            :pharmacyId = "pharmacyId">
        </ModalWindowHireDermatologist>
        <ModalWindowAddAppointment 
            @modal-closed = "mw_show_appointment = false" 
            :modal_show = "mw_show_appointment"
            :dermatologistIdToSend = "dermatologistId">
        </ModalWindowAddAppointment>
    </div>
</template>

<script>
import Button from './Button.vue';
import ModalWindowAddAppointment from './ModalViewAddAppointment'
import ModalWindowHireDermatologist from './ModalWindowHireDermatologist'
import Vue from 'vue'

// This variable will hold the reference to
// document's click handler
let handleOutsideClick

Vue.directive('closable', {
  bind (el, binding, vnode) {
    // Here's the click/touchstart handler
    // (it is registered below)
    handleOutsideClick = (e) => {
      e.stopPropagation()
      // Get the handler method name and the exclude array
      // from the object used in v-closable
      const { handler, exclude } = binding.value

      // This variable indicates if the clicked element is excluded
      let clickedOnExcludedEl = false
      exclude.forEach(refName => {
        // We only run this code if we haven't detected
        // any excluded element yet
        if (!clickedOnExcludedEl) {
          // Get the element using the reference name
          const excludedEl = vnode.context.$refs[refName]
          // See if this excluded element
          // is the same element the user just clicked on
          clickedOnExcludedEl = excludedEl.contains(e.target)
        }
      })

      // We check to see if the clicked element is not
      // the dialog element and not excluded
      if (!el.contains(e.target) && !clickedOnExcludedEl) {
        // If the clicked element is outside the dialog
        // and not the button, then call the outside-click handler
        // from the same component this directive is used in
        vnode.context[handler]()
      }
    }
    // Register click/touchstart event listeners on the whole page
    document.addEventListener('click', handleOutsideClick)
    document.addEventListener('touchstart', handleOutsideClick)
  },

  unbind () {
    // If the element that has v-closable is removed, then
    // unbind click/touchstart listeners from the whole page
    document.removeEventListener('click', handleOutsideClick)
    document.removeEventListener('touchstart', handleOutsideClick)
  }
})

export default {
    name: "Dermatologists",
    components: { Button, ModalWindowAddAppointment, ModalWindowHireDermatologist},
    props: {
        dermatologists : {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyId : null
    },
    data() {
        return {
            mw_show_appointment : false, // modal window cond-var
            mw_show_dermatologist : false, // modal window cond-var
            dermatologistId : null,
            selected_dermatologist: {}
        }
    },
    methods: {
        clickedId: function(id){
            this.dermatologistId = id;
            this.mw_show_appointment = true;
        },
        clickedOnRow: function(der){
            this.selected_dermatologist = der;
        },
        hireDermatologist: function(){   
            this.mw_show_dermatologist= true;
        },
        onOutOfFocus: function()
        {
            this.selected_dermatologist = {};
        },
        hiredDermatologist: function(dermatologist){
            this.mw_show_dermatologist = false;
            this.$emit('hired-dermatologist', dermatologist);
        }
    }
}

</script>

<style scoped>
#dermos {
    margin: 30px 10px 10px 10px;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

tr.selected {
	 background-color: rgba(155, 82, 151, 0.527);
}
</style>
