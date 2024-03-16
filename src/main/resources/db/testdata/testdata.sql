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
    ('Sister-in-law-1', 'Szwagierka'),
    ('Brother-in-law', 'Szwagier'),
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
     'Pogrzeb odbędzie się dnia 18.01.2024r., o godzinie 12:15 w Warszawie. ' ||
     'Po uroczystości pogrzebowej nastąpi odprowadzenie urny z prochami zmarłego.',
     'Rodzina prosi o nieskładanie kondolencji na cmentarzu.',
     true,
     'jan-kowalski-16',
     '1943rjipefiprijfihfudhhsdjs3',
     true
     ),
    (
        'Anastazja Nowakowska-Długowska',
        '1927-09-11',
        '2020-06-28',
        'Olsztyn',
        'Olsztyn',
        'FEMALE',
        'Lekarz ortopeda, przewodnicząca Rady Lekarskiej',
        false,
        null,
        'Pogrzeb odbędzie się dnia 02.07.2020r., o godzinie 10:15 w Olsztynie.' ||
        'Przed pogrzebem możliwe prywatne pożegnanie ze zmarłą w kaplicy cmentarnej.',
        'Zamiast zniczy prosimy o wpłatę na dowolny cel charytatywny.',
        true,
        'anastazja-nowakowska-długowska-17',
        'fh818rijfihfdh2905ug80hsdjs5',
        true
    ),
    (
        'Michał Bronowski',
        '2024-02-16',
        '2024-02-21',
        'Ustrzyki Dolne',
        'Sanok',
        'MALE',
        null,
        true,
        null,
        'Msza odbędzie się dnia 27.02.2024r., o godzinie 13:00 w kościele w Sanoku.' ||
        'Po uroczystości kondukt żałobny odprowadzi zmarłego na miejsce wiecznego spoczynku.',
        'Rodzina zaprasza najbliższych na stypę do lokalu.',
        true,
        'michał-bronowski-18',
        '21y2dfg44bfhmhsdjsjliuli7gg5',
        true
    );

INSERT INTO necrology_kinship
    (necrology_id, kinship_id)
VALUES
    (1L, 2L),
    (1L, 4L),
    (1L, 6L),
    (1L, 8L),
    (2L, 1L),
    (2L, 3L),
    (2L, 5L),
    (2L, 7L),
    (2L, 9L),
    (2L, 11L),
    (3L, 10L),
    (3L, 12L);

INSERT INTO candle
    (start_date_time, expiration_date_time, code, activated, necrology_id)
VALUES
    ('2024-02-01',
     '2024-03-01',
     'asfghjugkkkklo35hh7i8jdrr790',
     true,
    1L),
    ('2024-03-01',
     '2024-03-31',
     '33445ggasfghjug5778999hgfggf',
     true,
     1L),
    ('2024-03-06',
     '2024-04-05',
     'hoifioduf80ewuwefhfdsfdhfihe',
     true,
     1L),
    ('2024-03-08',
     '2024-04-07',
     '149cdijfe9f3jrn3jri39cisci99',
     false,
     1L),
    ('2024-03-03',
     '2024-04-02',
     '24232gdgdsfdhrytyffjjgjghihe',
     true,
     2L),
    ('2024-03-02',
     '2024-04-01',
     'wfegv54unjghetkm4mjyki7ryetw',
     false,
     2L);

INSERT INTO condolences
    (from_name, from_email, message, code, activated, necrology_id)
VALUES
    ('Ania z rodziną', 'ania-t@byom.de', 'Wyrazy współczucia dla Was wszystkich, trzymajcie się!', '12uwefvggh2hd2fhfgf7r72gf7g2', true, 1L),
    ('Basia z pracy', 'bigbarbi@byom.de', 'Szczerze współczuję odejścia Taty', 'd29390uohidhcuoh83823cgaukeo', true, 1L),
    ('Władek Nowak', 'wlad125@byom.de', 'Najszczersze kondolencje', 'vkfdrgrgl2hd2fhfg28472gf7g2f', false, 1L),
    ('Anonim', 'radefg@byom.de', 'Trzymajcie się!', 'dg17gbrdgth55ththtrh234254t4', true, 2L),
    ('Siostra Kasia', 'ksix234@byom.de', 'Nie wyobrażam sobie co czujecie, trzymajcie się...', '43d53hfhyjtj24rd457745vyefgd', false, 3L);

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
