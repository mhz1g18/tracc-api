import json

def parse(obj):
    otp = {}
    otp['name'] = obj['description']
    otp['createdBy'] = 'ADMIN'
    otp['calories'] = 0
    otp['quantity'] = 100
    otp['protein'] = 0
    otp['carbs'] = 0
    otp['fats']  = 0
    otp['_class'] = 'com.tracc.models.nutrition.Food'

    nutrients = obj['foodNutrients']

    for nutrient in nutrients:
        if nutrient['number'] == '204':
            otp['fats'] = nutrient['amount']
        elif nutrient['number'] == '205':
            otp['carbs'] = nutrient['amount']
        elif nutrient['number'] == '203':
            otp['protein'] = nutrient['amount']

    otp['calories'] = otp['fats'] * 9 + otp['carbs'] * 4 + otp['protein'] * 4
    otp['calories'] = round(otp['calories'], 2)

    return otp


input1 = open ('response.json')
input2 = open ('response2.json')
input3 = open ('response3.json')
input4 = open ('response4.json')

output = []

array1 = json.load(input1)
array2 = json.load(input2)
array3 = json.load(input3)
array4 = json.load(input4)

for item in array1:
    output.append(parse(item))

for item in array2:
    output.append(parse(item))

for item in array3:
    output.append(parse(item))

for item in array4:
    output.append(parse(item))

with open('foods.json', 'w') as outfile:
    json.dump(output, outfile)