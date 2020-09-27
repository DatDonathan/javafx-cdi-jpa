import {loadSchools} from "Rest/school/school-service"
import {schoolObservable} from "Model/observables"
import {setCurrentSchool} from "../../model/school/school-action-creator"

class SchoolTable extends HTMLTableElement {
    async connectedCallback() {
        schoolObservable
            .map(school => school.schools)
            .distinctUntilChanged()
            .subscribe(schools => this.render(schools))

        loadSchools()
    }
    clear() {
        this.deleteTHead()
        while (this.tBodies.length) {
            this.tBodies[0].remove()
        }
    }
    render(schools) {
        this.clear()
        this.classList.add("w3-table-all")
        const caption = this.createCaption()
        caption.innerText = "Schools"
        caption.classList.add("w3-xlarge")
        caption.classList.add("w3-light-blue")
        const head = this.createTHead()
        let row = head.insertRow()
        row.insertCell().innerText = "Id"
        row.insertCell().innerText = "Name"

        if (!this.tBodies.length) {
            this.createTBody()
        }
        const body = this.tBodies[0]
        schools.map(school => this.insertRow(body, school))
    }
    insertRow(body, school) {
        const row = body.insertRow()
        row.insertCell().innerText = school.id
        row.insertCell().innerText = school.name
        row.style = "cursor:pointer"
        row.onclick = () => this.click(school)
    }
    click(school) {
        console.log("clicked row", school)
    }
    schoolClicked(school, e) {
        console.log("school selected", school.id)
        setCurrentSchool(school.id)
    }
}
function clicked(e) {
    console.log("clicked...")
}
customElements.define("school-table", SchoolTable, {extends: "table"})