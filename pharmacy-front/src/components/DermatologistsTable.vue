<template>
    <div id="dermos">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Appointment</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="der.id" v-for="der in dermatologists">
                    <td>{{der.id}}</td>
                    <td>{{der.firstName }}</td>
                    <td>{{der.lastName}}</td> 
                    <td>{{der.rating}}</td>
                    <td><Button @action-performed="clickedId(der.id)" class="btn" text="New" bgd_color="rgba(15, 95, 72, 0.95)" :style="{color : 'rgba(255,255,255, 0.9)'}"></Button></td>
                </tr>
            </tbody>
        </table>
        <ModalWindowAddAppointment 
            @modal-closed = "modal_window_show = false" 
            :modal_show = "modal_window_show"
            :employeeIdToSend = "employeeId">
        </ModalWindowAddAppointment>
    </div>
</template>

<script>
import Button from './Button.vue';
import ModalWindowAddAppointment from './ModalViewAddAppointment'

export default {
    name: "Dermatologists",
    components: { Button, ModalWindowAddAppointment},
    props: {
        dermatologists : {
            type : Array,
            default() {
                return [];
            }
        }
    },
    data() {
        return {
            modal_window_show : false,
            employeeId : null,
        }
    },
    methods: {
        clickedId: function(id){
            this.employeeId = id;
            this.modal_window_show = true;
        },
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
</style>
