# Comparison Operations Plotter
# This script reads the results from 'resulter.txt' and plots the insert and search operations
#Themba Shongwe
# 2025-03-28
import pandas as pd
import matplotlib.pyplot as plt 
data = pd.read_csv('resulter.txt')

# Group by size and calculate min (best), max (worst), and mean (average)
grouped = data.groupby('size').agg({
    'insetCom': ['min', 'max', 'mean'],
    'searchCom': ['min', 'max', 'mean']
})
grouped.columns = ['_'.join(col).strip() for col in grouped.columns.values]

# Sizes (x-axis)
sizes = grouped.index

# Create figure
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 6))
fig.suptitle('AVL Tree Operations with Data Points', fontsize=16)

# --- Insert Operations ---
ax1.plot(
    sizes, grouped['insetCom_min'], 
    'go-',  # 'g'=green, 'o'=circle marker, '-'=solid line
    label='Best Case (O(1))'
)
ax1.plot(
    sizes, grouped['insetCom_max'], 
    'ro-',  # 'r'=red, 'o'=circle marker, '-'=solid line
    label='Worst Case (O(log n))'
)
ax1.plot(
    sizes, grouped['insetCom_mean'], 
    'bo-',  # 'b'=blue, 'o'=circle marker, '-'=solid line
    label='Avg Case (O(log n))'
)
ax1.set_xlabel('Number of Elements')
ax1.set_ylabel('Insert Operations')
ax1.set_title('Insert Complexity')
ax1.legend()
ax1.grid(True)

# --- Search Operations ---
ax2.plot(
    sizes, grouped['searchCom_min'], 
    'go-', 
    label='Best Case (O(1))'
)
ax2.plot(
    sizes, grouped['searchCom_max'], 
    'ro-', 
    label='Worst Case (O(log n))'
)
ax2.plot(
    sizes, grouped['searchCom_mean'], 
    'bo-', 
    label='Avg Case (O(log n))'
)
ax2.set_xlabel('Number of Elements')
ax2.set_ylabel('Search Operations')
ax2.set_title('Search Complexity')
ax2.legend()
ax2.grid(True)

plt.tight_layout()
plt.show()