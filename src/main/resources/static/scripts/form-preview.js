$(document).ready(function () {
    const $birthDate = $("#birthDate");
    const $deathDate = $("#deathDate");

    $deathDate.on('input', function () {
        const $deathInput = $(this);
        const rawDate = $deathInput.val();
        const formattedDate = moment(rawDate).format('DD.MM.YYYY');

        const $formPreviewDeathDate = $(".form-preview-death-date");
        $formPreviewDeathDate.text(formattedDate);
        $formPreviewDeathDate.css('font-weight', 'bold');
    });

    function countAge() {
        const $previewAge = $(".form-preview-age");

        const birthDateValue = $birthDate.val();
        const deathDateValue = $deathDate.val();

        if (birthDateValue !== "" && deathDateValue !== "") {
            const birthDate = moment(birthDateValue).format('DD.MM.YYYY');
            const deathDate = moment(deathDateValue).format('DD.MM.YYYY');

            const differenceInMillis = deathDate - birthDate;
            const differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);

            let ageText;

            if (differenceInDays < 1) {
                ageText = "w wieku 1 dnia";
            } else if (differenceInDays < 7) {
                ageText = "w wieku " + differenceInDays + " dni";
            } else if (differenceInDays < 30) {
                const weeks = Math.floor(differenceInDays / 7);
                ageText = "w wieku " + weeks + (weeks === 1 ? " tygodnia" : " tygodni");
            } else if (differenceInDays < 365) {
                const months = Math.floor(differenceInDays / 30);
                ageText = "w wieku " + months + (months === 1 ? " miesiąca" : " miesięcy");
            } else {
                const years = Math.floor(differenceInDays / 365);
                ageText = "w wieku " + years + (years === 1 ? " roku" : " lat");
            }

            $previewAge.text(ageText);
        }
    }

    countAge();

    $("input[name='gender']").on('change', function () {
        const $genderInput = $("input[name='gender']:checked");
        if ($genderInput.val() === 'FEMALE') {
            $("#form-preview-female").show();
            $("#form-preview-male").hide();
        } else if ($genderInput.val() === 'MALE') {
            $("#form-preview-male").show();
            $("#form-preview-female").hide();
        }
    });

    $("#kinship").on('input', function () {
        const $kinshipInput = $(this);
        const selectedOptions = $kinshipInput.find('option:selected');
        let kinshipDescription = "";
        selectedOptions.each(function () {
            kinshipDescription += $(this).text() + ", ";
        });
        kinshipDescription = kinshipDescription.replace(/, $/, '');
        $(".form-preview-kinship").text(kinshipDescription);
    });

    $("#addCrossAndLate").on('input', function () {
        $(".form-preview-crossAndLate").show();
    });

    $("#name").on('input', function () {
        const $nameInput = $(this);
        $(".form-preview-name").text($nameInput.val());
    });

    $("#pictureFile").on('change', function () {

        $("#form-preview-photo-i").hide();
        const $photoPreview = $("#form-preview-photo-img");
        $photoPreview.show();
        $photoPreview.attr('src', window.URL.createObjectURL(this.files[0]));
    });

    $("#title").on('input', function () {
        const $titleInput = $(this);
        $(".form-preview-title").text($titleInput.val());
    });

    $("#placeOfFuneral").on('input', function () {
        const $placeOfFuneralInput = $(this);
        $(".form-preview-place-of-funeral").text('Miejsce pogrzebu: ' + $placeOfFuneralInput.val());
    });

    $("#funeralDetails").on('input', function () {
        const $funeralDetailsInput = $(this);
        $(".form-preview-funeral-details").text($funeralDetailsInput.val());
    });

    $("#additionalInfo").on('input', function () {
        const $additionalInfoInput = $(this);
        $(".form-preview-additional-info").text($additionalInfoInput.val());
    });
});