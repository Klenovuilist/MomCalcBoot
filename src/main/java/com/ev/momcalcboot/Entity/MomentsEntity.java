package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "moments_db")
@AllArgsConstructor
@NoArgsConstructor
//@OptimisticLocking(type = OptimisticLockType.NONE )


public class MomentsEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "moments_nm")
    private double momentsNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", referencedColumnName = "id")
    private ThreadEntity thread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materals_id", referencedColumnName = "id")
    private MaterialsEntity materialsEntity;

    public static MomentsEntityBuilder builder() {
        return new MomentsEntityBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MomentsEntity;
    }

    public static class MomentsEntityBuilder {
        private int id;
        private double momentsNm;
        private ThreadEntity thread;
        private MaterialsEntity materialsEntity;

        MomentsEntityBuilder() {
        }

        public MomentsEntityBuilder id(int id) {
            this.id = id;
            return this;
        }

        public MomentsEntityBuilder momentsNm(double momentsNm) {
            this.momentsNm = momentsNm;
            return this;
        }

        public MomentsEntityBuilder thread(ThreadEntity thread) {
            this.thread = thread;
            return this;
        }

        public MomentsEntityBuilder materialsEntity(MaterialsEntity materialsEntity) {
            this.materialsEntity = materialsEntity;
            return this;
        }

        public MomentsEntity build() {
            return new MomentsEntity(this.id, this.momentsNm, this.thread, this.materialsEntity);
        }

        public String toString() {
            return "MomentsEntity.MomentsEntityBuilder(id=" + this.id + ", momentsNm=" + this.momentsNm + ", thread=" + this.thread + ", materialsEntity=" + this.materialsEntity + ")";
        }
    }


//    public Moments_db(double moments_nm, Thread_db thread, Materals_db materals_db) {
//        this.moments_nm = moments_nm;
//        this.thread = thread;
//        this.materals_db = materals_db;
//    }

    //    @OneToOne
//    @JoinColumn(name = "materials_id")
//    private Materals_db materals_db;
}
