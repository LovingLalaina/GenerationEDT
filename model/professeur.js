import mongoose from "mongoose";

const profSchema = new mongoose.Schema({
  idProf: {
    type: String,
    required: true,
    unique: true,
  },
  nom: {
    type: String,
    required: true,
  },
  prenom: {
    type: String,
    required: true
  },
  preferences: [
    {
      jour: {
        type: String,
        enum: ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"],
        required: true,
      },
      heures: {
        type: [String], // You can use an array of strings for hours
        required: true,
      },
    },
  ],
});

profSchema.virtual("edt", {
  ref: "Edt",
  localField: "_id",
  foreignField: "Prof"
})
profSchema.virtual("generate_edt", {
  ref: "generate_Edt",
  localField: "_id",
  foreignField: "Prof"
})

export default mongoose.model("Prof", profSchema);
