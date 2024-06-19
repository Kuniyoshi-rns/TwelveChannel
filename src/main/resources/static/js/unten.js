function openModal() {
    let gray_out = document.getElementById("fadeLayer");
    gray_out.style.visibility = "visible";
    setTimeout(addClass, 200);
}

function closeModal() {
    let modal = document.getElementById('modal');
    let gray_out = document.getElementById("fadeLayer");
    modal.classList.remove('is-show');
    gray_out.style.visibility ="hidden";
}

function addClass() {
    let modal = document.getElementById('modal');
    modal.classList.add('is-show');
}
//-------------------------------------------------------------
function openTagModal() {
    console.log('unten');
    let gray_out = document.getElementById("fadeLayer");
    gray_out.style.visibility = "visible";
    setTimeout(addTagClass, 200);
}

function closeTagModal() {
    let modal = document.getElementById('tag-modal');
    let gray_out = document.getElementById("fadeLayer");
    modal.classList.remove('is-show');
    gray_out.style.visibility ="hidden";
}

function addTagClass() {
    let modal = document.getElementById('tag-modal');
    modal.classList.add('is-show');
}
//-------------------------------------------------------------
function openDeleteModal() {
    console.log('unten');
    let gray_out = document.getElementById("fadeLayer");
    gray_out.style.visibility = "visible";
    setTimeout(addDeleteClass, 200);
}

function closeDeleteModal() {
    let modal = document.getElementById('delete-modal');
    let gray_out = document.getElementById("fadeLayer");
    modal.classList.remove('is-show');
    gray_out.style.visibility ="hidden";
}

function addDeleteClass() {
    let modal = document.getElementById('delete-modal');
    modal.classList.add('is-show');
}
