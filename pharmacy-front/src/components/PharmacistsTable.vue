<template>
    <div id="pahrmas">
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
                </tr>
            </thead>

            <tbody>
                <tr :key="phar.id" v-for="phar in pharmacists" @click="clickedOnRow(phar)" v-bind:class="{selected : selected_pharmacist.id===phar.id}">
                    <td>{{phar.id}}</td>
                    <td>{{phar.username}}</td>
                    <td>{{phar.email}}</td>
                    <td>{{phar.firstName}}</td>
                    <td>{{phar.lastName}}</td>
                    <td><i v-bind:class="phar.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                    <td>{{phar.rating}}</td>
                </tr>
            </tbody>
        </table>
        <!-- <Button
            @action-performed="hirePharmacist()"
            class="btn" 
            text="Hire pharmacist" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
        </Button> -->
    </div>
</template>

<script>
import Button from './Button.vue';

export default {
    name: "PharmacistsTable",
    components: { Button },
    props: {
        pharmacists : {
            type : Array,
            default() {
                return [];
            }
        }
    },
    data() {
        return {
            modal_window_show_pharmacist : false,
            pharmacistId : null,
            selected_pharmacist: {},
        }
    },
    methods: {
        clickedId: function(id){
            this.pharmacistId = id;
            this.modal_window_show_appointment = true;
        },
        clickedOnRow: function(phar){
            this.selected_pharmacist = phar;
        },
        onOutOfFocus: function()
        {
            this.selected_pharmacist = {};
        }
    }
}

</script>

<style scoped>
#pahrmas {
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
