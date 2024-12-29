PGDMP          
            |            MomentNM    16.2    16.2 1    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    33066    MomentNM    DATABASE     �   CREATE DATABASE "MomentNM" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE "MomentNM";
                postgres    false            �            1259    33174    bolt_entity    TABLE     �   CREATE TABLE public.bolt_entity (
    id integer NOT NULL,
    bolt_name character varying(20),
    bolt_limit bigint,
    id_user bigint,
    comment character varying,
    data_create date,
    classbolt double precision
);
    DROP TABLE public.bolt_entity;
       public         heap    postgres    false            �            1259    33173    bolt_entity_id_seq    SEQUENCE     �   CREATE SEQUENCE public.bolt_entity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.bolt_entity_id_seq;
       public          postgres    false    228            �           0    0    bolt_entity_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.bolt_entity_id_seq OWNED BY public.bolt_entity.id;
          public          postgres    false    227            �            1259    33130    databasechangelog    TABLE     Y  CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);
 %   DROP TABLE public.databasechangelog;
       public         heap    postgres    false            �            1259    33135    databasechangeloglock    TABLE     �   CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         heap    postgres    false            �            1259    33072    materals_db    TABLE     �  CREATE TABLE public.materals_db (
    id bigint NOT NULL,
    limit_strength bigint DEFAULT 0,
    materials character varying,
    user_id bigint,
    strength_class double precision,
    comments character varying(100),
    k_depth double precision,
    data_create timestamp without time zone,
    coefffricthread double precision,
    coefffricbolthead double precision,
    safetyfactor double precision,
    materialscrew character varying,
    limit_screw bigint,
    class_screw double precision
);
    DROP TABLE public.materals_db;
       public         heap    postgres    false            �            1259    33122    materals_db_id_seq    SEQUENCE     �   ALTER TABLE public.materals_db ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.materals_db_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �            1259    33067 
   moments_db    TABLE     �   CREATE TABLE public.moments_db (
    id bigint NOT NULL,
    moments_nm double precision,
    thread_id bigint NOT NULL,
    materals_id bigint NOT NULL
);
    DROP TABLE public.moments_db;
       public         heap    postgres    false            �            1259    33111    moments_db_id_seq    SEQUENCE     �   ALTER TABLE public.moments_db ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.moments_db_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    33160    sqrew_entity    TABLE       CREATE TABLE public.sqrew_entity (
    id integer NOT NULL,
    sqrew_name character varying(20),
    sqrew_limit bigint,
    sqrew_depth double precision,
    id_user bigint,
    comment character varying,
    data_create date,
    classsqrew double precision
);
     DROP TABLE public.sqrew_entity;
       public         heap    postgres    false            �            1259    33159    sqrew_entity_id_seq    SEQUENCE     �   CREATE SEQUENCE public.sqrew_entity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.sqrew_entity_id_seq;
       public          postgres    false    226            �           0    0    sqrew_entity_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.sqrew_entity_id_seq OWNED BY public.sqrew_entity.id;
          public          postgres    false    225            �            1259    33103 	   thread_db    TABLE     �   CREATE TABLE public.thread_db (
    id bigint NOT NULL,
    thread character varying,
    step_thread double precision,
    d_midlethread double precision,
    d_bolt double precision,
    d_hole double precision,
    d_head double precision
);
    DROP TABLE public.thread_db;
       public         heap    postgres    false            �            1259    33110    thread_db_id_seq    SEQUENCE     �   ALTER TABLE public.thread_db ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.thread_db_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    33146    users    TABLE     �   CREATE TABLE public.users (
    id bigint NOT NULL,
    user_name character varying(25),
    password_user character varying(30),
    role_user character varying(30),
    data_user date
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    33158    users_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            =           2604    33177    bolt_entity id    DEFAULT     p   ALTER TABLE ONLY public.bolt_entity ALTER COLUMN id SET DEFAULT nextval('public.bolt_entity_id_seq'::regclass);
 =   ALTER TABLE public.bolt_entity ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    228    228            <           2604    33163    sqrew_entity id    DEFAULT     r   ALTER TABLE ONLY public.sqrew_entity ALTER COLUMN id SET DEFAULT nextval('public.sqrew_entity_id_seq'::regclass);
 >   ALTER TABLE public.sqrew_entity ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    226    226            �          0    33174    bolt_entity 
   TABLE DATA           j   COPY public.bolt_entity (id, bolt_name, bolt_limit, id_user, comment, data_create, classbolt) FROM stdin;
    public          postgres    false    228   m=       �          0    33130    databasechangelog 
   TABLE DATA           �   COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
    public          postgres    false    221   {>       �          0    33135    databasechangeloglock 
   TABLE DATA           R   COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public          postgres    false    222   �@       �          0    33072    materals_db 
   TABLE DATA           �   COPY public.materals_db (id, limit_strength, materials, user_id, strength_class, comments, k_depth, data_create, coefffricthread, coefffricbolthead, safetyfactor, materialscrew, limit_screw, class_screw) FROM stdin;
    public          postgres    false    216   	A       �          0    33067 
   moments_db 
   TABLE DATA           L   COPY public.moments_db (id, moments_nm, thread_id, materals_id) FROM stdin;
    public          postgres    false    215   �C       �          0    33160    sqrew_entity 
   TABLE DATA           {   COPY public.sqrew_entity (id, sqrew_name, sqrew_limit, sqrew_depth, id_user, comment, data_create, classsqrew) FROM stdin;
    public          postgres    false    226   ;G       �          0    33103 	   thread_db 
   TABLE DATA           c   COPY public.thread_db (id, thread, step_thread, d_midlethread, d_bolt, d_hole, d_head) FROM stdin;
    public          postgres    false    217   �G       �          0    33146    users 
   TABLE DATA           S   COPY public.users (id, user_name, password_user, role_user, data_user) FROM stdin;
    public          postgres    false    223   �H       �           0    0    bolt_entity_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.bolt_entity_id_seq', 54, true);
          public          postgres    false    227            �           0    0    materals_db_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.materals_db_id_seq', 123, true);
          public          postgres    false    220            �           0    0    moments_db_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.moments_db_id_seq', 703, true);
          public          postgres    false    219            �           0    0    sqrew_entity_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.sqrew_entity_id_seq', 12, true);
          public          postgres    false    225            �           0    0    thread_db_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.thread_db_id_seq', 37, true);
          public          postgres    false    218            �           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 9, true);
          public          postgres    false    224            ?           2606    33071    moments_db Moments_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.moments_db
    ADD CONSTRAINT "Moments_pkey" PRIMARY KEY (id);
 C   ALTER TABLE ONLY public.moments_db DROP CONSTRAINT "Moments_pkey";
       public            postgres    false    215            M           2606    33181    bolt_entity bolt_entity_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.bolt_entity
    ADD CONSTRAINT bolt_entity_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.bolt_entity DROP CONSTRAINT bolt_entity_pkey;
       public            postgres    false    228            E           2606    33139 0   databasechangeloglock databasechangeloglock_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT databasechangeloglock_pkey;
       public            postgres    false    222            K           2606    33167    sqrew_entity sqrew_entity_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.sqrew_entity
    ADD CONSTRAINT sqrew_entity_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.sqrew_entity DROP CONSTRAINT sqrew_entity_pkey;
       public            postgres    false    226            A           2606    33076    materals_db strengths_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.materals_db
    ADD CONSTRAINT strengths_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.materals_db DROP CONSTRAINT strengths_pkey;
       public            postgres    false    216            C           2606    33109    thread_db thread_db_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.thread_db
    ADD CONSTRAINT thread_db_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.thread_db DROP CONSTRAINT thread_db_pkey;
       public            postgres    false    217            G           2606    33150    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    223            I           2606    33152    users users_user_name_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_name_key UNIQUE (user_name);
 C   ALTER TABLE ONLY public.users DROP CONSTRAINT users_user_name_key;
       public            postgres    false    223            R           2606    33182 $   bolt_entity bolt_entity_id_user_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.bolt_entity
    ADD CONSTRAINT bolt_entity_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.bolt_entity DROP CONSTRAINT bolt_entity_id_user_fkey;
       public          postgres    false    223    228    4679            P           2606    33153 $   materals_db materals_db_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.materals_db
    ADD CONSTRAINT materals_db_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.materals_db DROP CONSTRAINT materals_db_user_id_fkey;
       public          postgres    false    223    4679    216            N           2606    33117    moments_db materials_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.moments_db
    ADD CONSTRAINT materials_id_fkey FOREIGN KEY (materals_id) REFERENCES public.materals_db(id) NOT VALID;
 F   ALTER TABLE ONLY public.moments_db DROP CONSTRAINT materials_id_fkey;
       public          postgres    false    215    216    4673            Q           2606    33168 &   sqrew_entity sqrew_entity_id_user_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.sqrew_entity
    ADD CONSTRAINT sqrew_entity_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.sqrew_entity DROP CONSTRAINT sqrew_entity_id_user_fkey;
       public          postgres    false    226    223    4679            O           2606    33112    moments_db thread_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.moments_db
    ADD CONSTRAINT thread_id_fkey FOREIGN KEY (thread_id) REFERENCES public.thread_db(id) NOT VALID;
 C   ALTER TABLE ONLY public.moments_db DROP CONSTRAINT thread_id_fkey;
       public          postgres    false    215    217    4675            �   �   x�m�AN1E��)���q��%8A7�F�-k�	�`ˢH�w�"�zύpB�f:,����?	�n��0�2" F���� ��u�B�X_u�/�g��hĺ���Y? UJ�z��䯆FG����f[sW��h�`���1$�dǕ����d�
3��4B}�քY����޷����L����b�ȸ�����f�1�Vs��Ԟ��<��c
]��K, ��ILa$Q�����z�u��Z/]bjP,^z��ǲ��      �   Y  x����j�@���S����avFwm��Bo�B/e���l��B^���CHMA���_���v�Ƈ\>����:m������竩���X����c���n�͛����~�=*�v���A���ދ�L
����O�no�J_���C��f�P��Y�8��Z^��(�k��r��"�փ^��=�}ؕ�2
:����F�#Rʺ$�\�\��\�zG���*��n��ҭ�a�߲c���`��F�S;�s�3�p&&��O������ȿӾ�[��r��]\=�bY���/Ԛ��rO`�feO�L��vH��Z|_[S�59o=[�8��Э˯ysi	��s�R��;�
PM�lAj@&�&ȑX'���4ց��in���7_۝����Pk�9��/I����w�^��E\��h�"���$68�z��y�7֒�ݥ�]��q�'�+�\��g▐�b탴F�n�yoM�%�-���6M��s�W���ϷOu���]���L��s�A�,���Sf��:Z� ,�3c^�I���9���(�v|q�i���	;XZ�'%���@"̩�,��>G��x+(�����]�Z�� ��F      �      x�3�L��"�=... U�      �   �  x��U?OA��~�-���{o��LGbbG�	��QcaҘh"	�$bBoD���Wx��|3w ��m ��������7�B��[�m���3�v_�yh:�޼5�Cؘ>���@˷9݋ ��[�*��� ��xFv�	RK!��1��9��n�/ DD���d��LD�vy	�<<�p��jw5�r&�r*G��I�dpK��gO�PU�A���M� �̟in��4+	�|�3R6�Y}�JDZ!w7�֪b��A�N9o�w��ʵ�����=�>�.ۤ}Z]�|e��nU�.ɱ��l�LÈ�9��|��I]EH���Nzj\�_�4���Cc!���TN*qM���i�J�u�C��^&��~�7[۩����	ܐ^�Ã+2rfo�0��+QhH	lT�R��S�r '��f�cU�
'�Á�v�p]��c��l�!,F��y͗��`�����O�n�D����tM���+W�>/T�q3�&Z��1�`�;Vr;���O�b_�5�R��u�§X��JS��]{qE4𮃇��|I}�����2X��C9��*�J�˯��,��[�S���5L�E/��R�Fwݎc��xg��togo:]5����U� ������"����U�jy�܋m�ۦi�Q�.      �   �  x�u�Iv$!D��a�!�]����9Tu�7��@�ג���d֎^k��\��͞�6�d�,	ǂ;DgK��k�����9R5a5��mh�ϙh�R���ӕ1��rj#<�.�oh/�X�֒���V��K:�i�����>J��� �()S�P�����¦@[�[�D\N���@=y!�����WE3���ϩ�ފ�∞�S|��n�۾�9
��	��/��T�:\�xj���?O�t갞N\I)���.���8�"cZ�?�m��p
�3}���#�ԝO��c�"���]�d��D!�P�|`��Igm#�gXD�+�y��$FjEq*��Yl��}�:n�C���Iw�#&#��;5�]�幈�����ݙ����2���E��Mic�'������=p���D���ê�����z ;|��� ���H����"5�W�$D#��.�#b��h	�Hʉ!Y-zH�X~Ys�5|;GD��(/!
�E�у{���pFV�ihʱ*P#c]ݯ�Q�j:	�6��C�� ���@�O׫�%s>�h�=-@,O����v`{�&��6q�l���6y=���z�&��6س��lw��p[�&�\�m"m��+v���A�vo �s����^�g��� p1�1��-��BC
�=� A�Q���W��@{�����pj�
�&\��&�؄�{�{
��}�r�>Q�v�����\�W
P�m�>��� ��g ���g �^���|�k��ڞf��N{𓹬z'�7ڪ�Fbߌ���4p����@E(!����������D��b�}?�oƇHl>#j�鈃�ǲ/��l$�yE�nd�J�"6���e��F�xyosJgէ8:Y�ʯaٷ���R}��8���J��      �   �   x�m���@E�z���2�4a�u+Є�=y���\V@I<N����~��\F���*��tQȈ2�fbZ��V0��k�6O|�گ�� �mS��|����-�<�����-�>���?��ecbE�?@���gO����y�<R�M�����ߖ1u%�JJ�      �      x�%��0B�qS���np��q4��<�C,?H�> �clL,����,<�����`R����l"��N����@�<��r빍~n��}q%�10[vi�ї�8�ƍ�Z����WD���� *X 1      �   �   x�3�tL����LD"��Lt,t�̸�9�s*9�dj^:���	gXfv"g���4�N,�H�,��f�a�E@C��$��9��g^J"gN&�Đ�༰�¾�-6p秡*��54���I,jh��\F�A�%��E �hs�=... �zMd     