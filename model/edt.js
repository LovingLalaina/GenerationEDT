import mongoose, { Schema } from "mongoose";

const edtSchema = new mongoose.Schema({
  idEdt: {
    type: String,
    required: true,
    unique: true,
  },
  professeur: {
    type: Schema.Types.ObjectId,
    ref: "Prof",
  },
  matiere: {
    type: Schema.Types.ObjectId,
    ref: "Mat", 
  },
  // idNiveau: {
  //   type: mongoose.Schema.Types.ObjectId,
  //   ref: "Niveau", 
  // },
});


export default mongoose.model("Edt", edtSchema);
