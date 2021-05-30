<template>
    <div id="dermos">
        <label v-if="dermatologists.length == 0"><span style="font-size: 20px;">There are no hired dermatologists.</span></label>
        <div v-if="dermatologists.length != 0">
            <table class="table table-hover">
            <!-- v-closable="{exclude: [], handler: 'onOutOfFocus'}"> -->
                <thead>
                    <tr>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Work time</th>
                        <th scope="col">Appointments</th>
                        <th scope="col">Absences</th>
                    </tr>
                </thead>

                <tbody>
                    <tr  :key="der.id" v-for="der in dermatologists" @click="clickedOnRow(der)" v-bind:class="{selected : selected_dermatologist.id===der.id}">
                        <td>{{der.username}}</td>
                        <td>{{der.email}}</td>
                        <td>{{der.firstName}}</td>
                        <td>{{der.lastName}}</td>
                        <td><i v-bind:class="der.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td>
                        <td>
                            <star-rating
                                data-toggle="tooltip" 
                                data-placement="top" 
                                :title="der.rating"
                                active-color="rgba(155, 82, 151, 0.527)"
                                :inline="true"
                                :star-size="25"
                                :read-only="true"
                                :show-rating="false"
                                :rating="der.rating"
                                :increment="0.1"
                            ></star-rating>
                        </td>
                        <td style="cursor:pointer" @click="showEmployments(der)"><i class="fa fa-clock-o" aria-hidden="true"></i> {{der.workTime.startTime}} - {{der.workTime.endTime}}</td>
                        <td><button @click="showMore(der)"><i class="fa fa-book fa-lg" aria-hidden="true"></i></button></td>
                        <td><button @click="showAbscene(der)"><i class="fa fa-question-circle fa-lg" aria-hidden="true"></i></button></td>
                    </tr>
                </tbody>
            </table>
            <Button
                @action-performed="fireDermatologist()"
                class="btn" 
                text="Fire dermatologist" 
                bgd_color="rgba(15, 95, 72, 0.85)" 
                :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
            </Button>
            <ModalWindowAddAppointment 
                @modal-closed = "mw_show_more = false"
                @appointment-deleted = "deleteAppointment"
                :modal_show = "mw_show_more"
                :dermatologistToSend = "dermatologist"
                :appointments = "dermosAppointments">
            </ModalWindowAddAppointment>
            <ModalWindowAbsence 
                @modal-closed = "mw_show_abscence = false"
                @absence-remove-from-list = "removeAbsence"
                :modal_show = "mw_show_abscence"
                :absences = "dermosAbsenceRequests">
            </ModalWindowAbsence>
            <ModalWindowDermatologistsEmployments 
                @modal-closed = "mw_show_employments = false"
                :modal_show = "mw_show_employments"
                :dermatologist = "dermatologist"
                :employments = "dermosEmployments"
                :pharmacyId = "pharmacyId">
            </ModalWindowDermatologistsEmployments>
            
        </div>
        <Button
            @action-performed="hireDermatologist()"
            class="btn" 
            text="Hire dermatologist" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'right'}">
        </Button>
        <ModalWindowHireDermatologist
            @hired-dermatologist = "hiredDermatologist"
            @modal-closed = "mw_hire_dermatologist = false" 
            :modal_show = "mw_hire_dermatologist"
            :pharmacyId = "pharmacyId"
            :dermatologists = "dermatologistsToHire">
        </ModalWindowHireDermatologist>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from './Button.vue';
import ModalWindowAddAppointment from './ModalViewAddAppointment';
import ModalWindowHireDermatologist from './ModalWindowHireDermatologist.vue';
import StarRating from 'vue-star-rating';
import ModalWindowAbsence from './ModalWindowAbsence.vue'
import ModalWindowDermatologistsEmployments from './ModalWindowDermatologistsEmployments.vue'
// import Vue from 'vue'

// // This variable will hold the reference to
// // document's click handler
// let handleOutsideClick

// Vue.directive('closable', {
//   bind (el, binding, vnode) {
//     // Here's the click/touchstart handler
//     // (it is registered below)
//     handleOutsideClick = (e) => {
//       e.stopPropagation()
//       // Get the handler method name and the exclude array
//       // from the object used in v-closable
//       const { handler, exclude } = binding.value

//       // This variable indicates if the clicked element is excluded
//       let clickedOnExcludedEl = false
//       exclude.forEach(refName => {
//         // We only run this code if we haven't detected
//         // any excluded element yet
//         if (!clickedOnExcludedEl) {
//           // Get the element using the reference name
//           const excludedEl = vnode.context.$refs[refName]
//           // See if this excluded element
//           // is the same element the user just clicked on
//           clickedOnExcludedEl = excludedEl.contains(e.target)
//         }
//       })

//       // We check to see if the clicked element is not
//       // the dialog element and not excluded
//       if (!el.contains(e.target) && !clickedOnExcludedEl) {
//         // If the clicked element is outside the dialog
//         // and not the button, then call the outside-click handler
//         // from the same component this directive is used in
//         vnode.context[handler]()
//       }
//     }
//     // Register click/touchstart event listeners on the whole page
//     document.addEventListener('click', handleOutsideClick)
//     document.addEventListener('touchstart', handleOutsideClick)
//   },

//   unbind () {
//     // If the element that has v-closable is removed, then
//     // unbind click/touchstart listeners from the whole page
//     document.removeEventListener('click', handleOutsideClick)
//     document.removeEventListener('touchstart', handleOutsideClick)
//   }
// })

export default {
    name: "Dermatologists",
    components: { 
        Button, 
        ModalWindowAddAppointment, 
        ModalWindowHireDermatologist, 
        StarRating,
        ModalWindowAbsence,
        ModalWindowDermatologistsEmployments
    },
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
            mw_show_more : false, // modal window cond-var
            mw_hire_dermatologist : false, // modal window cond-var
            mw_show_abscence : false,
            mw_show_employments: false,
            dermatologist : {},
            dermosAppointments : [],
            dermosAbsenceRequests : [],
            dermosEmployments : [],
            selected_dermatologist: { id : -1},
            dermatologistsToHire : [],
        }
    },
    methods: {
        showMore: function(derm){
             client({
                    url: "appointments/getAvailableDermAppointments",
                    method: "GET",
                    params: { employeeUsername: derm.username, pharmacyId : this.pharmacyId}
                }).then((response) => {
                    this.dermosAppointments = response.data;
                    derm['pharmacyId'] = this.pharmacyId;
                    this.dermatologist = derm;
                    this.mw_show_more = true;
                });
        },
        showAbscene : function(derm){
            client({
                url: "absences/allRequestedAbsencesForEmployee",
                method: "GET",
                params: { username : derm.username}
            }).then((response) => {
                this.dermosAbsenceRequests = response.data;
                this.mw_show_abscence = true;
            });
        },
        showEmployments : function(derm){
            client({
                url: "employments/employmentsOfDermatologist",
                method: "GET",
                params: { username : derm.username}
            }).then((response) => {
                this.dermosEmployments = response.data;
                this.dermatologist = derm;
                this.mw_show_employments = true;
            });
        },
        removeAbsence : function(absence){
            for(const absenceIndex in this.dermosAbsenceRequests){
                if(this.dermosAbsenceRequests[absenceIndex].id === absence.id){
                    this.dermosAbsenceRequests.splice(absenceIndex, 1);
                    break;
                }
            }
        },
        clickedOnRow: function(der){
            this.selected_dermatologist = der;
        },
        hireDermatologist: function(){   
            this.mw_hire_dermatologist= true;
            if(this.dermatologistsToHire.length == 0)
                client({
                    url: "dermatologist/all",
                    method: "GET"
                }).then((response) => {
                    response.data.forEach(dermatologist => {
                        let foundId = -1;
                        this.dermatologists.forEach(hiredDermos => {
                            if(dermatologist.id == hiredDermos.id){
                                foundId = dermatologist.id;
                            }
                        });
                        if(foundId == -1)
                            this.dermatologistsToHire.push(dermatologist);
                    });
                });
        },
        onOutOfFocus: function()
        {
            this.selected_dermatologist = { id : -1};
        },
        hiredDermatologist: function(dermatologist){
            for(const dermIter in this.dermatologistsToHire){
                if(this.dermatologistsToHire[dermIter].id === dermatologist.id){
                    this.dermatologistsToHire.splice(dermIter, 1);
                    break;
                }
            }
            this.mw_hire_dermatologist = false;
            this.$emit('hired-dermatologist', dermatologist);
        },
        fireDermatologist : function(){
            if(this.selected_dermatologist.id != -1){
                client({
                    url: "employments/" + this.selected_dermatologist.employmentId,
                    method: "DELETE"
                }).then((response) => {
                    this.dermatologistsToHire.push(this.selected_dermatologist);
                    this.$toasted.show("Dermatologist fired!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    this.$emit('fired-dermatologist', this.selected_dermatologist);
                });
            }
            else
                this.$toasted.show("Please select dermatologist to fire!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
        },
        deleteAppointment : function(appointmentId){
            for(const datumIndex in this.dermosAppointments){
                if(this.dermosAppointments[datumIndex].id === appointmentId){
                    this.dermosAppointments.splice(datumIndex, 1);
                    break;
                }
            }
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
