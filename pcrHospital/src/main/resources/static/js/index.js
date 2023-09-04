$('#search').click(function () {
    let sidoNm = document.getElementById('sidoNm').value;
    let sgguNm = document.getElementById('sgguNm').value;
    getHospital(sidoNm, sgguNm)
})

let getHospital = async (sidoNm, sgguNm) => {
    let response = await fetch(`http://localhost:8000/api/hospital?sidoNm=${sidoNm}&sgguNm=${sgguNm}`);
    let responseParsed = await response.json();
    setHospital(responseParsed);
}

let setHospital = (hospitals) => {
    let tbody = document.getElementById('hospital-tbody');
    tbody.innerHTML = '';
    hospitals.forEach((hospital) => {
        let tr = document.createElement('tr');
        let td1 = document.createElement('td');
        td1.innerText = hospital.yadmNm;
        let td2 = document.createElement('td');
        td2.innerText = hospital.dgmPrscPsblYn;
        let td3 = document.createElement('td');
        td3.innerText = hospital.addr;
        tr.append(td1);
        tr.append(td2);
        tr.append(td3);
        tbody.append(tr);
    })
}

let getSggNm = async (sidoNm) => {
    let response = await fetch(`http://localhost:8000/api/sgguNm?sidoNm=${sidoNm}`);
    let responseParsed = await response.json();
    setSggNm(responseParsed)
}

let setSggNm = (sgguNms) => {
    let sgguNmDom = document.getElementById('sgguNm');
    sgguNmDom.innerHTML = '';
    sgguNms.forEach((sgguNm) => {
        let option = document.createElement('option');
        option.text = sgguNm;
        sgguNmDom.append(option);
    })

}

let sidoNm = document.getElementById('sidoNm');
sidoNm.addEventListener('change', (e)=> {
    let sidoNm = e.target.value;
    getSggNm(sidoNm)
})