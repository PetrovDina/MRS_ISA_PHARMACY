<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover" v-closable="{exclude: [], handler: 'onOutOfFocus'}">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col"></th>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications" @click="clickedOnRow(med)" 
                @dblclick="dblClickedOnRow()" v-bind:class="{selected : selectedMedication.id===med.id}">
                    <td>{{ med.id }}</td>
                    <td>{{ med.name }}</td>
                    <td>{{ med.manufacturer }}</td>
                    <td>
                        {{ med.prescriptionReq ? "required" : "not required" }}
                    </td>
                    <td>{{ med.form }}</td>
                </tr>
            </tbody>
        </table>
        <ModalWindowMedicationDetail 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modalWindowShowed"
            :medicationId = "selectedMedicationToPass.id">
        </ModalWindowMedicationDetail>
    </div>
</template>

<script>

import ModalWindowMedicationDetail from "../components/ModalWindowMedicationDetail";
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
    name: "MedicationsView",

    components: {ModalWindowMedicationDetail},

    props: ["medications"],

    data() {
        return {
            selectedMedication : {},
            selectedMedicationToPass: {},
            modalWindowShowed: false,
        };
    },

    mounted() {},

    methods: {
        reserveMedication(med) {
            this.$router.push({
                name: "MedicationReservationView",
                params: {
                    medication: med
                },
            });
        },

        clickedOnRow : function(med)
        {
            this.selectedMedication = med; 
            this.$emit('clicked-on-row', med);
        },

        dblClickedOnRow: function()
        {
            this.selectedMedicationToPass = this.selectedMedication;
            this.modalWindowShowed = true;
        },

        closeModalWindow: function()
        {
            this.modalWindowShowed = false;
        },

        onOutOfFocus: function()
        {
            this.selectedMedication = {};
        }
    },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#medicationsViewDiv {
    margin: 30px 60px 30px 60px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.reserveButton{
    background-color: rgba(155, 82, 151, 0.527); 
    padding: 5px;
    border-radius: 5px;

}

table {
    cursor: pointer;
}

tr.selected {
	 background-color: #aaaaaa;
}

</style>

