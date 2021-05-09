<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Medication Storage</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <MedicationsTableSupplier 
        :supplierStorageItems = "supplierStorageItems"
        @add-med-to-supplier = "addSupplierStorageItem"
        @supp-item-deleted = "deleteSuppStorageItem"
        ></MedicationsTableSupplier>

    </div>
    
</template>

<script>

import { client } from "@/client/axiosClient";
import MedicationsTableSupplier from '../components/MedicationsTableSupplier.vue';

export default {
    name: "SupplierMedicationReserves",

    components: {MedicationsTableSupplier},

    data() {
        return {
            supplierStorageItems: [],
        };
    },

    props: {
        
    },

    methods: {

        addSupplierStorageItem : function(med, quantity)
        {
            alert(med.id);
            console.log(med);
            alert(quantity);

            for(let i = 0; i < this.supplierStorageItems.length; i++)
            {
                console.log(this.supplierStorageItems[i]);
                if(this.supplierStorageItems[i].medication.id == med.id)
                {
                    this.supplierStorageItems[i].quantity = this.supplierStorageItems[i].quantity + parseInt(quantity);
                    client
                    ({
                        url: "supplierStorageItem/update",
                        method: "PUT",
                        data: this.supplierStorageItems[i]
                    }).then((response) => {
                        alert("Successfully added more medication to storage.")
                    })
                    return;
                }
            }

            // Posto je takav DTO moram da dobavim id supplier-a preko njegovog username-a
            let suppId;
            if(this.supplierStorageItems == []) // Ako ne postoji ni jedan item moram da dobavim preko servera id suppliera
            {
                client
                ({
                    url: "supplier/getId/" + localStorage.user,
                    method: "GET",
                }).then((response) => {
                    suppId = response.data;
                })
            }
            else    // Ako postoji onda mogu od bilo kog itema da dobavim
            {
                suppId = this.supplierStorageItems[0].supplierId;
            }

            // Pravim DTO objekat za kreiranje 
            let suppStorageItemDTO = {
                "id" : null,
                "quantity" : quantity,
                "medication" : med,
                "supplierId" : suppId
            }

            //Saljem zahtev za kreiranje
            client
            ({
                url: "supplierStorageItem/create",
                method: "POST",
                data: suppStorageItemDTO
            }).then((response) => {
                alert("Successfully added new medication to storage.")
                this.supplierStorageItems.push(response.data);
            })       
        },

        deleteSuppStorageItem : function(id)
        {
            client
            ({
                url: "supplierStorageItem/delete/" + id,
                method: "DELETE",
            }).then((response) => {
                alert("Successfully removed medication from storage.");
                var j = 0;
                for (j = 0; j < this.supplierStorageItems.length; j++)
                {
                    if(this.supplierStorageItems[j].id == id)
                    {
                        this.supplierStorageItems.splice(j, 1);
                    }
                }
            })  
        }
        
    },

    mounted() {

        client
        ({
            url: "supplierStorageItem/fromSupplier/" + localStorage.user,
            method: "GET"
        }).then((response) => {
            this.supplierStorageItems = response.data;
            console.log(this.supplierStorageItems[0].medication);
        })

    },
};

</script>


