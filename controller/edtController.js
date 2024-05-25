import Edt from "../model/edt.js";

export const addEdt = async (req, res) => {
  try {
    const { idEdt, matiere, professeur } = req.body;
    const newEdt = new Edt({
      idEdt,
      matiere,
      professeur
   
    });
    const saveEdt = await newEdt.save();

    res.status(200).json({ saveEdt });
  } catch (error) {
    res.status(400).send(error);
  }
};

export const deleteEdt = async (req, res) => {
  try {
    const edt = await Edt.findByIdAndDelete(req.params.id);
    res.status(200).json({ edt });
  } catch (error) {
    res.status(500).send(error);
  }
};

export const editEdt = async (req, res) => {
  try {
    const edt = await Edt.findByIdAndUpdate(
      req.params.id,
      {
        $set: req.body,
      },
      {
        new: true,
      }
    );
    res.status(200).json({ edt });
  } catch (error) {
    res.status(500).send(error);
  }
};

export const edtById = async (req, res) => {
  try {
    const edt = await Edt.findById(req.params.id);
    res.status(200).json({ edt });
  } catch (error) {
    res.status(500).send(error);
  }
};

export const getAllEdt = async (req, res) => {
  try {
    const getAllEdt = await Edt.find().populate("professeur").populate("matiere");
    res.status(200).json(getAllEdt );
  } catch (error) {
    res.status(500).send(error);
  }
};
