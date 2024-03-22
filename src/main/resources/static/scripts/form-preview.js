$(document).ready(function () {
    const $birthDate = $("#birthDate");
    const $deathDate = $("#deathDate");

    $birthDate.on('input', function () {
        countAge();
    });

    $deathDate.on('input', function () {
        countAge();
    });

    function countAge() {
        const $previewAge = $(".form-preview-age");

        const birthDateValue = $birthDate.val();
        const deathDateValue = $deathDate.val();

        if (birthDateValue !== "" && deathDateValue !== "") {
            const birthDate = moment(birthDateValue, 'YYYY-MM-DD');
            const deathDate = moment(deathDateValue, 'YYYY-MM-DD');

            const differenceInMillis = deathDate.diff(birthDate);
            const differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);

            let ageText;

            if (differenceInDays < 1) {
                ageText = "1 dnia";
            } else if (differenceInDays < 7) {
                ageText = differenceInDays + " dni";
            } else if (differenceInDays < 30) {
                const weeks = Math.floor(differenceInDays / 7);
                ageText = weeks + (weeks === 1 ? " tygodnia" : " tygodni");
            } else if (differenceInDays < 365) {
                const months = Math.floor(differenceInDays / 30);
                ageText = months + (months === 1 ? " miesiąca" : " miesięcy");
            } else {
                const years = Math.floor(differenceInDays / 365);
                ageText = years + (years === 1 ? " roku" : " lat");
            }

            $previewAge.text(ageText);
        }
    }

    $deathDate.on('input', function () {
        const $deathInput = $(this);
        const rawDate = $deathInput.val();
        const formattedDate = moment(rawDate).format('DD.MM.YYYY');

        const $formPreviewDeathDate = $(".form-preview-death-date");
        $formPreviewDeathDate.text(formattedDate);
        $formPreviewDeathDate.css('font-weight', 'bold');
    });

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

    $("#addCrossAndLate").on('click', function () {
        const $crossLateInput = $(".form-preview-crossAndLate");
        if ($(this).is(':checked')) {
            $crossLateInput.show();
        } else {
            $crossLateInput.hide();
        }

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
        let $placeOfFuneralPreview = $(".form-preview-place-of-funeral");

        if ($placeOfFuneralInput.val()) {
            $placeOfFuneralPreview.text('Miejsce pogrzebu: ' + $placeOfFuneralInput.val()).show();
        } else {
            $placeOfFuneralPreview.hide();
        }
    });

    $("#placeOfOrigin").on('input', function () {
        const $placeOfOriginInput = $(this);
        let $placeOfOriginPreview = $(".form-preview-place-of-origin");

        if ($placeOfOriginInput.val()) {
            $placeOfOriginPreview.text('Z: ' + $placeOfOriginInput.val()).show();
        } else {
            $placeOfOriginPreview.hide();
        }
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