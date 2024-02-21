INSERT INTO kinship
    (name, pl_name)
VALUES
    ('Grandma', 'Babcia'),
    ('Grandpa', 'Dziadek'),
    ('Mother-in-law', 'Teściowa'),
    ('Father-in-law', 'Teść'),
    ('Mom', 'Mama'),
    ('Dad', 'Tata'),
    ('Aunt', 'Ciocia'),
    ('Uncle', 'Wujek'),
    ('Daughter', 'Córka'),
    ('Son', 'Syn'),
    ('Sister', 'Siostra'),
    ('Brother', 'Brat'),
    ('Daughter-in-law', 'Synowa'),
    ('Son-in-law', 'Zięć'),
    ('Brother-in-law', 'Szwagier'),
    ('Sister-in-law-1', 'Szwagierka'),
    ('Sister-in-law-2', 'Bratowa'),
    ('Fiancée', 'Narzeczona'),
    ('Fiancé', 'Narzeczony'),
    ('Girlfriend', 'Dziewczyna'),
    ('Boyfriend', 'Chłopak'),
    ('Partner-Female', 'Partnerka'),
    ('Partner-Male', 'Partner'),
    ('Colleague-Female', 'Koleżanka'),
    ('Colleague-Male', 'Kolega'),
    ('Acquaintance-Female', 'Znajoma'),
    ('Acquaintance-Male', 'Znajomy');

INSERT INTO necrology
    (name, birth_date, death_date, place_of_origin, place_of_funeral, gender, title, add_cross_and_late,
     removing_date, funeral_details, additional_info, accepted_terms, necrology_identifier, code, activated)
VALUES
    (
     'Jan Kowalski',
     '1948-07-20',
     '2024-01-01',
     'Stargard',
     'Warszawa',
     'MALE',
     'Artysta, malarz, filantrop',
     true,
     '2024-01-23 23:59:59',
     'Pogrzeb odbędzie się dnia 18.01.2024r., o godzinie 12:15 w Warszawie, w Kaplicy na Cmentarzu Bródnowskim. ' ||
     'Po uroczystosci pogrzebowej nastąpi odproadzenie urny z prochami zmarłego.',
     'Rodzina prosi o nieskładanie kondolencji na cmentarzu.',
     true,
     'jan-kowalski-1',
     '1943rjipefiprijfihfudhhsdjs3',
     true
     );

INSERT INTO necrology_kinship
    (necrology_id, kinship_id)
VALUES
    (1L, 2L),
    (1L, 4L),
    (1L, 6L),
    (1L, 8L);

INSERT INTO candle
    (start_date_time, expiration_date_time, code, activated, necrology_id)
VALUES
    ('2024-02-01',
     '2024-03-03',
     'asfghjugkkkklo35hh7i8jdrr790',
     true,
    1L),
    ('2024-02-02',
     '2024-03-04',
     '33445ggasfghjug5778999hgfggf',
     true,
     1L),
    ('2024-01-06',
     '2024-02-05',
     'hoifioduf80ewuwefhfdsfdhfihe',
     true,
     1L),
    ('2024-02-10',
     '2024-03-11',
     '149cdijfe9f3jrn3jri39cisci99',
     false,
     1L);

INSERT INTO condolences
    (from_name, from_email, message, code, activated, necrology_id)
VALUES
    ('Ania z rodziną', 'ania-t@byom.de', 'Wyrazy współczucia dla Was wszystkich, trzymajcie się!', '12uwefvggh2hd2fhfgf7r72gf7g2', true, 1L),
    ('Basia z pracy', 'bigbarbi@byom.de', 'Szczerze współczuję odejścia Taty', 'd29390uohidhcuoh83823cgaukeo', true, 1L),
    ('Władek Nowak', 'wlad125@byom.de', 'Najszczersze kondolencje', 'vkfdrgrgl2hd2fhfg28472gf7g2f', false, 1L);

INSERT INTO person
    (name, email, password)
VALUES
    ('Wiktor', 'admin@byom.de', '{noop}hard'),
    ('Alojzy', 'moderAlek@gmail.coml', '{noop}tajneHaslo');

INSERT INTO person_role
(person_id, role)
VALUES
    (1L, 'ROLE_ADMIN'),
    (2L, 'ROLE_MODERATOR');
