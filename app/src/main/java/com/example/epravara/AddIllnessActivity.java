package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddIllnessActivity extends AppCompatActivity {

    String[] disease = { "Blood Disease", "Cancer", "Endocrine Disease", "Eye Disease",
            "Gastroinstestinal", "Heart Disease", "HyperLipidemia (Chol)", "Infectious Disease",
            "Kidney Disease", "Lung Disease", "Neurological Disease", "Psychiatric", "Rheumatic Disease",
            "Skin Disease", "Other"};

    String[] blood = { "Anemia", "Bleeding Problem", "Clotting Problem", "Leukemia", "Other"};

    String[] cancer = { "Basal Cell Carinoma", "Bladder Cancer", "Breast Cancer", "Colorectal Cancer",
            "Endometrial Cancer", "Kidney Cancer", "Leukemia", "Liver Cancer",
            "Lung Cancer", "Melanoma", "Non-Hodgkin Lymphoma", "Pancreatic Cancer", "Prostrate Cancer",
            "Squamous Cell Carcinoma", "Thyroid Cancer", "Other"};

    String[] endocrine = { "Adrenal Disease", "Diabetes", "Thyroid Disease", "Other"};

    String[] eye = { "Cataracts", "Glaucoma", "Retinal Disease", "Other"};

    String[] gastroinstestinal = { "Cirrhosis", "Colitis", "Crohn's", "Diverculitis",
            "Gall Bladder Disease", "Hemorrhoids", "Hepatitis", "Jaundice",
            "Pancreatitis", "Reflux", "Ulcer", "Other"};

    String[] heart = { "Angina", "Atherosclerosis", "Embolism", "Heart Attack",
            "Heart Failure", "High BloodPressure", "Murmur", "Pacemaker",
            "Phlebitis", "Rhythm Problem", "Valve Problem", "Other"};

    String[] hyperLipidemia = { "High Cholesterol", "Low Cholesterol", "Other"};

    String[] infectious = { "AIDS", "Diptheria", "Gonorrhea", "Meningitis",
            "Pneumonia", "Polio", "Rheumatic Fever", "Small Pox",
            "Syphillis", "Tuberculosis", "Whooping Cough", "Other"};

    String[] kidney = { "Bladder Infections", "Cystitis", "Glomerulonephritis", "Kidney Failure",
            "Kidney Infections", "Kidney Stones", "Polycystic Kidney", "Prostate Disease",
            "Pyelonephritis", "Other"};

    String[] lung = { "Asthma", "Bronchitis", "Emphysema", "Hay Fever", "Other"};

    String[] neurological = { "Migraines", "Neuropathy", "Seizure", "Stroke", "TIA", "Other"};

    String[] psychiatric = { "Bipolar", "Depression", "Generalized Anxiety Disorder", "Major Depressive Disorder",
            "Obsessive-Conpulsive Disorder", "Persistent Depressive Disorder", "Post-Traumatic Stress Disorder",
            "Schizophrenia", "Social Anxiety Disorder", "Other"};

    String[] rheumatic = { "Bursitis", "Disc Disease", "Gout", "Lupus",
            "Osteoarthritis", "Rhematoid Arthritis", "Seleroderma", "Solatica",
            "Vasculitis", "Other"};

    String[] skin = { "Basal Cell Carinoma", "Eczema", "Hives", "Melanoma",
            "Psoriasis", "Squamous Cell Carinoma", "Other"};

    private TextView conditionDate;
    private Button btnAddIllness;
    private RadioGroup radio;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);

        final Spinner illnessSpinner = findViewById(R.id.illnessTypeSpinner);
        final Spinner nameSpinner = findViewById(R.id.illnessNameSpinner);
        final TextInputEditText edtType = findViewById(R.id.edtIllnessType);
        final TextInputEditText edtName = findViewById(R.id.edtIllnessName);
        conditionDate = findViewById(R.id.illnessDate);
        btnAddIllness = findViewById(R.id.addIllness);

        ArrayAdapter main = new ArrayAdapter(this, android.R.layout.simple_spinner_item, disease);
        main.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        illnessSpinner.setAdapter(main);

        final ArrayAdapter bloodAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, blood);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter cancerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cancer);
        cancerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter endocrineAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, endocrine);
        endocrineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter eyeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, eye);
        eyeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter gastoAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gastroinstestinal);
        gastoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter heartAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, heart);
        heartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter hyperAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hyperLipidemia);
        hyperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter infectiousAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, infectious);
        infectiousAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter kidneyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kidney);
        kidneyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter lungAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lung);
        lungAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter neurologicalAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, neurological);
        neurologicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter pyschiatricAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, psychiatric);
        pyschiatricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter rheumaticAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rheumatic);
        rheumaticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter skinAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, skin);
        skinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        illnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                // Depend on first spinner value set adapter to 2nd spinner
                if (position == 0) {
                    nameSpinner.setAdapter(bloodAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 1) {
                    nameSpinner.setAdapter(cancerAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 9) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 10) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 11) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 12) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 13) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 14) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 15) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 2) {
                    nameSpinner.setAdapter(endocrineAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 3) {
                    nameSpinner.setAdapter(eyeAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 4) {
                    nameSpinner.setAdapter(gastoAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 9) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 10) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 11) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 5) {
                    nameSpinner.setAdapter(heartAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 9) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 10) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 11) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 6) {
                    nameSpinner.setAdapter(hyperAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 7) {
                    nameSpinner.setAdapter(infectiousAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 9) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 10) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 11) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 8) {
                    nameSpinner.setAdapter(kidneyAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 9) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 10) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 9) {
                    nameSpinner.setAdapter(lungAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 10) {
                    nameSpinner.setAdapter(neurologicalAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 11) {
                    nameSpinner.setAdapter(pyschiatricAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 12) {
                    nameSpinner.setAdapter(rheumaticAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 7) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 8) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 13) {
                    nameSpinner.setAdapter(skinAdapter);
                    edtType.setVisibility(View.GONE);
                    edtName.setVisibility(View.GONE);
                    nameSpinner.setVisibility(View.VISIBLE);
                    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 1) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 2) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 3) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 4) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 5) {
                                edtName.setVisibility(View.GONE);
                            } else if (position == 6) {
                                edtName.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 14) {
                    edtType.setVisibility(View.VISIBLE);
                    edtName.setVisibility(View.VISIBLE);
                    nameSpinner.setVisibility(View.GONE);
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                conditionDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        conditionDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddIllnessActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        radio = findViewById(R.id.radioGroup);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("ConditionIllness");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = conditionDate.getText().toString();
                String illnesstypeData = illnessSpinner.getSelectedItem().toString();
                String illnessnameData = nameSpinner.getSelectedItem().toString();

                String otherType = edtType.getText().toString();
                String otherName = edtName.getText().toString();

                int selectedRadioButtonID = radio.getCheckedRadioButtonId();

                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                final String selectedRadioButtonText = selectedRadioButton.getText().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddIllnessActivity.this, "Condition/Illness Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (otherType.length() != 0){
                    illnesstypeData = "";
                }
                if (otherName.length() != 0){
                    illnessnameData = "";
                }

                progressDialog.setTitle("Adding New Condition/Illness");
                progressDialog.setMessage("Please wait while adding a condition..");
                progressDialog.show();

                final String finalIllnesstypeData = illnesstypeData;
                final String finalIllnessnameData = illnessnameData;

                final String finalOtherType = otherType;
                final String finalOtherName = otherName;

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Illness illness = new Illness(
                                finalDate,
                                finalIllnesstypeData,
                                finalIllnessnameData,
                                finalOtherType,
                                finalOtherName,
                                selectedRadioButtonText
                        );
                        reference.child(userID).child(finalDate).setValue(illness).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddIllnessActivity.this, "Condition/Illness Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddIllnessActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}